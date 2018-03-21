import {Component, OnInit, ViewChild} from '@angular/core';
import {ParkingListFilter} from '../../model/filter/parking-list-filter';
import {LocationFieldComponent} from './location-field/location-field.component';
import {FormGroup} from '@angular/forms';
import {RadiusFieldComponent} from './radius-field/radius-field.component';

@Component({
    selector: 'app-parking-list-filter',
    templateUrl: './parking-list-filter.component.html',
    styleUrls: ['./parking-list-filter.component.css'],
})
export class ParkingListFilterComponent implements OnInit {

    @ViewChild('locationField') locationField: LocationFieldComponent;

    @ViewChild('radiusField') radiusField: RadiusFieldComponent;
    public filter: ParkingListFilter;
    private formGroup: FormGroup;

    constructor() {
    }

    ngOnInit() {
        this.formGroup = new FormGroup({
            firstName: this.locationField.control,
        });
        this.locationField.value.subscribe(value => {
            console.log(value);
        });
        this.radiusField.value.subscribe(value => {
            console.log(value);
        });
    }
}
