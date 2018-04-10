import {Component, OnInit, ViewChild} from '@angular/core';
import {Parking} from '../../model/view/parking';

@Component({
    selector: 'app-parking-map',
    templateUrl: './parking-map.component.html',
    styleUrls: ['./parking-map.component.css']
})

export class ParkingMapComponent implements OnInit {
    lat: number;
    lng: number;
    parkings: Parking[];
    dir = undefined;

    constructor() {
    }

    ngOnInit() {

    }

    getDirection(lat, lng) {
        this.dir = {
            origin: {lat: this.lat, lng: this.lng},
            destination: {lat: lat, lng: lng}
        };
    }

    round(floatNumber: number): number {
        return Math.floor(floatNumber) / 1000;
    }
}
