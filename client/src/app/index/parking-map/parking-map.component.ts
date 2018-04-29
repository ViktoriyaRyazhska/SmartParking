import {Component, OnInit} from '@angular/core';
import {Parking} from '../../model/view/parking';
import {ParkingService} from '../../parking.service';
import {MatSnackBar} from '@angular/material';
import {DataserviceService} from '../dataservice.service';
import {StatisticsService} from '../../statistic/statistics.service';

const numberOfDaysByDefault = 30;

@Component({
    selector: 'app-parking-map',
    templateUrl: './parking-map.component.html',
    styleUrls: ['./parking-map.component.css']
})

export class ParkingMapComponent implements OnInit {
    lat = 49.843977;
    lng = 24.026318;
    parkings: Parking[] = [];
    bestParkings: Parking[] = [];
    dir = undefined;
    distance: string;
    radius: number;
    visibility: boolean;
    infoWindowOpened = null;
    count = 0;

    constructor(private parkingService: ParkingService,
                private dataService: DataserviceService,
                private statisticService: StatisticsService,
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
                    this.dataService.pushParkingsToDataService(this.parkings);
                }, error => {
                    console.log(error);
                });
                setTimeout(() => this.findBestParkingsByLocation(
                    this.lat,
                    this.lng,
                    this.radius,
                    numberOfDaysByDefault), 2000);
            });
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
            this.snackBar.open('Unfortunately, there are no parkings in radius of ' + radius / 1000 + ' km', null, {
                duration: 3000
            });
        } else {
            this.snackBar.open('The most popular parking in radius ' + radius / 1000 + ' km is on ' + this.bestParkings[0].street + ' ' +
                this.bestParkings[0].building, null, {
                duration: 3000
            });
        }
    }

    findBestParkingsByLocation(latitude: number, longitude: number, radius: number, days: number) {
        this.statisticService.getBestParkingsByLocation(latitude, longitude, radius, days)
            .subscribe(bestParkiings => {
                this.bestParkings = bestParkiings;
                this.dataService.pushBestParkingsToDataService(this.bestParkings);
                this.checkingForParkingAvailability(this.bestParkings.length, radius);
            });
    }

}
