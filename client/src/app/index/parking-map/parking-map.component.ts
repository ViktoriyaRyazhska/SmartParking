import {Component, OnInit, ViewChild} from '@angular/core';
import {ParkingService} from '../../parking.service';
import {Parking} from '../../model/view/parking';
import {icon, latLng, marker, polyline, tileLayer} from 'leaflet';
import {ParkingListFilterComponent} from '../parking-list-filter/parking-list-filter.component';

@Component({
    selector: 'app-parking-map',
    templateUrl: './parking-map.component.html',
    styleUrls: ['./parking-map.component.css']
})

export class ParkingMapComponent implements OnInit {
    title: string = 'My first AGM project';
    lat: number;
    lng: number;

    @ViewChild('filter') private filter: ParkingListFilterComponent;

    parkings: Parking[];

    constructor(private parkingService: ParkingService) {
    }

    ngOnInit() {
        this.findMe();
    }


    findMe() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition((position => {
                this.getPosition(position);
            }));
        }
    }

    private getPosition(position: Position) {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        this.parkingCoordinates();
    }

    parkingCoordinates() {
        this.parkingService.getParkingsNearby(this.lat, this.lng, 10000)
            .subscribe((response) => {
                this.parkings = response.body;
            }, error => {
                console.log(error);
            });
        console.log(this.parkings);
    }
}
