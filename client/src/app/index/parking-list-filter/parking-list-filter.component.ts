import {Component, OnInit, ViewChild} from '@angular/core';
import {Location, LocationFieldComponent} from './location-field/location-field.component';
import {FormGroup} from '@angular/forms';
import {RadiusFieldComponent} from './radius-field/radius-field.component';
import {PriceRange, PriceRangeFieldComponent} from './price-range-field/price-range-field.component';
import {Subject} from 'rxjs/Subject';

const earthRadius = 6371e3;

@Component({
    selector: 'app-parking-list-filter',
    templateUrl: './parking-list-filter.component.html',
    styleUrls: ['./parking-list-filter.component.css'],
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

    private present: boolean;

    private distance: number;

    constructor() {
    }

    public get value(): ParkingListFilter {
        return this.internalValue;
    }

    ngOnInit() {
        this.locationField.valueChanges.subscribe(location => {
            for (let city of this.locationField.cities) {
                let cityLat;
                let cityLng;
                let geocoder = new google.maps.Geocoder();
                geocoder.geocode({'address': city + ', Ukraine'}, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        cityLat = results[0].geometry.location.lat();
                        cityLng = results[0].geometry.location.lng();
                    } else {
                        alert('Something got wrong ' + status);
                    }
                });

                this.distance = this.getDistanceBetweenPoint(cityLat, location.latitude, cityLng, location.longitude);
                this.distance = 9;
                if (this.distance <= 10) {
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

    public getDistanceBetweenPoint(lat1, lat2, lon1, lon2): number {
        let f1 = this.toRadians(lat1);
        let f2 = this.toRadians(lat2);
        let l1 = this.toRadians((lat2 - lat1));
        let l2 = this.toRadians((lon2 - lon1));

        var a = Math.sin(l1 / 2) * Math.sin(l1 / 2) +
            Math.cos(f1) * Math.cos(f2) *
            Math.sin(l2 / 2) * Math.sin(l2 / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        var d = earthRadius * c;
        return d;
    }

    private toRadians(number): number {
        return number * Math.PI / 180;
    }

    public get radiusMax(): number {
        return this.radiusField.max;
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
