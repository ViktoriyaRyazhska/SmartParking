///<reference path="../../../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import {Component, OnInit, ViewChild} from '@angular/core';
import {Location, LocationFieldComponent} from './location-field/location-field.component';
import {FormGroup} from '@angular/forms';
import {RadiusFieldComponent} from './radius-field/radius-field.component';
import {PriceRange, PriceRangeFieldComponent} from './price-range-field/price-range-field.component';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

const earthRadius = 6371;

@Component({
    selector: 'app-parking-list-filter',
    templateUrl: './parking-list-filter.component.html',
    styleUrls: ['./parking-list-filter.component.css']
})

export class ParkingListFilterComponent implements OnInit {

    @ViewChild('locationField')
    private locationField: LocationFieldComponent;

    @ViewChild('radiusField')
    private radiusField: RadiusFieldComponent;

    @ViewChild('priceRangeField')
    private priceRangeField: PriceRangeFieldComponent;

    private readonly formGroup = new FormGroup({});

    private readonly valueChangesSubject = new Subject<ParkingListFilter>();

    public readonly valueChanges = this.valueChangesSubject.asObservable();

    private internalValue: ParkingListFilter;

    private present: Boolean;

    private distance: number;

    constructor() {
    }

    public get value(): ParkingListFilter {
        return this.internalValue;
    }

    ngOnInit() {
        this.locationField.valueChanges.subscribe(location => {
            for (let city of this.locationField.cityLatLng) {
                this.distance = this.getDistanceBetweenPoint(city.latitude, city.longitude, location.latitude, location.longitude);
                if (this.distance <= 20) {
                    this.present = true;
                    break;
                } else {
                    this.present = false;
                }
            }

            if (this.present) {
                this.internalValue = new ParkingListFilter(location, this.priceRangeField.value, this.radiusField.value);
                this.valueChangesSubject.next(this.internalValue);
                localStorage.setItem('locationLatitude', location.latitude.toString());
                localStorage.setItem('locationLongtitude', location.longitude.toString());

            } else {
                window.alert('Our api doesn\'t support this location, unfortunately :(');
            }


        });
        this.priceRangeField.valueChanges.subscribe(priceRange => {
            if (this.locationField.value) {
                this.internalValue = new ParkingListFilter(this.locationField.value, priceRange, this.radiusField.value);
                this.valueChangesSubject.next(this.internalValue);
                if (priceRange.min != undefined)
                    localStorage.setItem('minValue', priceRange.min.toString());
                if (priceRange.max != undefined)
                    localStorage.setItem('maxValue', priceRange.max.toString());
            }
        });
        this.radiusField.valueChanges.subscribe(radius => {
            if (this.locationField.value) {
                this.internalValue = new ParkingListFilter(this.locationField.value, this.priceRangeField.value, radius);
                this.valueChangesSubject.next(this.internalValue);
            }
            localStorage.setItem('radius', radius.toString());
        });
    }

    private getDistanceBetweenPoint(startLat, startLong, endLat, endLong): number {
        let dLat = this.toRadians((endLat - startLat));
        let dLong = this.toRadians((endLong - startLong));

        startLat = this.toRadians(startLat);
        endLat = this.toRadians(endLat);

        var a = this.haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * this.haversin(dLong);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        var d = earthRadius * c;
        console.log(d);
        return d;
    };

    private haversin(val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    private toRadians(number): number {
        return number * Math.PI / 180;
    }

    public get radiusMax(): number {
        return this.radiusField.max;
    }

    getLatLng(address: String) {
        console.log(address);
        let geocoder = new google.maps.Geocoder();
        return Observable.create(observer => {
            geocoder.geocode({'address': address + ', Ukraine'}, function (results, status) {
                if (status = google.maps.GeocoderStatus.OK) {
                    observer.next(results[0].geometry.location);
                    observer.complete();
                } else {
                    console.log('Error - ', results, ' & Status - ', status);
                    observer.next({});
                    observer.complete();
                }
            });
        });
    }

}

export class ParkingListFilter {

    public readonly location: Location;
    public readonly priceRange: PriceRange;
    public readonly radius: number;

    public constructor(location: Location, priceRange: PriceRange, radius: number) {
        this.location = location;
        this.priceRange = priceRange;
        this.radius = radius;
    }

}
