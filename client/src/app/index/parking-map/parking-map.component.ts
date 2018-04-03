import {Component, OnInit} from '@angular/core';
import {ParkingService} from '../../parking.service';
import {Parking} from '../../model/view/parking';
import { latLng, tileLayer } from 'leaflet';

@Component({
    selector: 'app-parking-map',
    templateUrl: './parking-map.component.html',
    styleUrls: ['./parking-map.component.css']
})
export class ParkingMapComponent implements OnInit {
    title: string = 'My first AGM project';
    lat: number;
    lng: number;
    parkings: Parking[];
    options = {
        layers: [
            tileLayer('http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
                maxZoom: 20,
                subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
                detectRetina: true
            })
        ],
        zoom: 7,
        center: latLng([ 46.879966, -121.726909 ])
    };

    constructor(private parkingService: ParkingService) {
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
        this.parkingCoord();
    }

    parkingCoord() {
        this.parkingService.getParkingsNearby(this.lat, this.lng, 30000)
            .subscribe((response) => {
                this.parkings = response.body;
            }, error => {
                console.log(error);
            });
        console.log(this.parkings)
    }
}
