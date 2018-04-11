import {Component, OnInit, ViewChild} from '@angular/core';
import {Parking} from '../../model/view/parking';
import {Browser} from 'leaflet';

const MiToKm = 1.60934;

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
    distance: string;

    constructor() {
    }

    ngOnInit() {

    }

    getDirection(lat, lng) {
        this.dir = {
            origin: {lat: this.lat, lng: this.lng},
            destination: {lat: lat, lng: lng},
            drivingOptions: {
                departureTime: new Date(Date.now()),
                trafficModel: 'pessimistic'
            },
            travelMode: 'DRIVING'
        };
    }

    round(floatNumber: number): number {
        return Math.floor(floatNumber) / 1000;
    }

    revertMileToKm(distance: any): number {
        distance = distance.substr(0, distance.indexOf(' '));
        Number.parseInt(distance);
        distance *= MiToKm;
        return Math.floor(distance * 10) / 10;
    }
}
