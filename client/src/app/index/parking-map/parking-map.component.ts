import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-parking-map',
    templateUrl: './parking-map.component.html',
    styleUrls: ['./parking-map.component.css']
})
export class ParkingMapComponent implements OnInit {
    title: string = 'My first AGM project';
    lat: number;
    lng: number;

    constructor() {
    }

    ngOnInit() {
        this.findMe();
    }

    findMe() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position => {
                this.showPosition(position);
            }));
        }
    }

    private showPosition(position: Position) {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
    }
}
