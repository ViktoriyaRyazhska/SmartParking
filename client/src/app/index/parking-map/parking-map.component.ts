import {Component, OnInit} from '@angular/core';
import {Parking} from '../../model/view/parking';
import {ParkingService} from '../../parking.service';
import {MatSnackBar} from "@angular/material";

@Component({
    selector: 'app-parking-map',
    templateUrl: './parking-map.component.html',
    styleUrls: ['./parking-map.component.css']
})


export class ParkingMapComponent implements OnInit {
    lat = 49.843977;
    lng = 24.026318;
    parkings: Parking[] = [];
    dir = undefined;
    distance: string;
    radius: number;
    visibility: boolean;
    infoWindowOpened = null;
    count = 0;

    constructor(private parkingService: ParkingService,
                private snackBar: MatSnackBar) {
    }

    ngOnInit() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                if ((localStorage.getItem('locationLatitude') && localStorage.getItem('locationLongtitude') != null)) {
                    this.lat = +localStorage.getItem('locationLatitude');
                    this.lng = +localStorage.getItem('locationLongtitude');
                } else {
                    this.lat = position.coords.latitude;
                    this.lng = position.coords.longitude;
                }
                if (localStorage.getItem('radius') != undefined) {
                    this.radius = +localStorage.getItem('radius') * 1000;
                } else {
                    this.radius = 5000;
                }
                this.parkingService.getParkingsNearby(this.lat, this.lng, this.radius).subscribe((response) => {
                    this.parkings = response.body;
                    this.checkingForParkingAvailability(this.parkings.length, this.radius);
                }, error => {
                    console.log(error);
                });

            })
        }

    }

    getDirection(lat, lng) {
        this.visibility = true;
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

    clearDirection() {
        this.visibility = false;
    }

    showInfoWindow(infoWindow) {
        if (this.infoWindowOpened === infoWindow) {
            return;
        }

        if (this.infoWindowOpened !== null) {
            this.infoWindowOpened.close();
        }

        this.infoWindowOpened = infoWindow;
    }

    checkingForParkingAvailability(numberOfParkings: number, radius: number) {
        if (numberOfParkings < 1) {
            this.snackBar.open('Unfortunately, there are no parking in radius of ' + radius / 1000 + " km", null, {
                duration: 4000
            });
        } else {
            this.snackBar.open('Was found ' + numberOfParkings + " parkings in radius " + radius / 1000 + " km", null, {
                duration: 4000
            });
        }
    }

}
