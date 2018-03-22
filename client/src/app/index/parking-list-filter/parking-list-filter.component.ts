import {Component, OnInit, ViewChild} from '@angular/core';
import {Location, LocationFieldComponent} from './location-field/location-field.component';
import {FormGroup} from '@angular/forms';
import {RadiusFieldComponent} from './radius-field/radius-field.component';
import {PriceRange, PriceRangeFieldComponent} from './price-range-field/price-range-field.component';
import {Observable} from 'rxjs/Observable';

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

    constructor() {
    }

    public get location(): Observable<Location> {
        return this.locationField.value;
    }

    public get radius(): Observable<number | null> {
        return this.radiusField.value;
    }

    public get priceRange(): Observable<PriceRange> {
        return this.priceRangeField.value;
    }

    ngOnInit() {
    }
}
