import {Component, OnInit} from '@angular/core';

import {Parking} from '../parking';
import {ParkingService} from '../parking.service';
import {GeoLocationService} from '../geo-location.service';


@Component({
  selector: 'app-parkings',
  templateUrl: './parkings.component.html',
  styleUrls: ['./parkings.component.css']
})
export class ParkingsComponent implements OnInit {

  parkings: Parking[];
    filteredParkings: Parking[] = [];

    latitude: number = 0;
    longitude: number = 0;

    constructor(private parkingService: ParkingService,
                private geoLocation: GeoLocationService) {
    }

  ngOnInit() {
    console.log("qaz");
    this.getParkings();
  }

  getParkingsArray(): Parking[]{
      return this.parkings;
  }

  getParkings(): void {
    this.parkingService.getParkings()
        .subscribe(parkings => {
            this.parkings = parkings;
            this.filteredParkings = parkings;
        });
      // .subscribe(parkings => {console.log(parkings[1].latitude)});
      this.geoLocation.getLocation()
          .subscribe((position: Position) => {
              this.latitude = position.coords.latitude;
              this.longitude = position.coords.longitude;
              this.filteredParkings = [];
              this.filterByGeoLocation();
          });
  }

    getDistanceFromLatLonInKm(currentLatitude: number, currentLongitude: number): boolean {
        if (this.latitude === 0 || this.longitude === 0) {
            return true;
        }
        let R = 6371; // Radius of the earth in km
        let dLat = this.deg2rad(currentLatitude - this.latitude);  // deg2rad below
        let dLon = this.deg2rad(currentLongitude - this.longitude);
        let a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(this.deg2rad(currentLatitude)) * Math.cos(this.deg2rad(this.latitude)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
        ;
        let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        let d = R * c; // Distance in km

        return d < 10;
    }

    deg2rad(deg) {
        return deg * (Math.PI / 180)
    }

    filterByGeoLocation() {
        this.parkings.forEach((parking) => {
            if (this.getDistanceFromLatLonInKm(parking.longitude, parking.latitude)) {
                this.filteredParkings.push(parking);
            }
        });
    }

}
