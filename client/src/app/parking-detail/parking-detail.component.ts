import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

import {Parking} from '../model/view/parking';
import {ParkingService} from "../parking.service";
import { Spot } from '../model/view/spot';

@Component({
  selector: 'app-parking-detail',
  templateUrl: './parking-detail.component.html',
  styleUrls: ['./parking-detail.component.css']
})
export class ParkingDetailComponent implements OnInit {

  parking: Parking;
  spots: Spot[];
  // type: String;
  // fullnessBarMessage: String;
  // max: number;
  // value:number;

  constructor(
    private route: ActivatedRoute,
    private parkingService: ParkingService,
    private location: Location
  ) { }

  ngOnInit() {
    this.getParking();
    this.getSpots();
   // this.fullnessBarCount();
  }

  // fullnessBarCount(): void {
  //   this.max = this.parking.numberSpots;
  //   this.value =  this.parking.numberSpots -  this.parking.numberAvailableSpots;
  //   if (this.value < (this.max * 0.6)) {
  //     this.type = 'success';
  //     this.fullnessBarMessage = 'Welcome!';
  //   } else if (this.value < (this.max*0.75)) {
  //     this.type = 'info';
  //     this.fullnessBarMessage = 'You have a chance!';
  //   } else if (this.value < (this.max*0.99)) {
  //     this.type = 'warning';
  //     this.fullnessBarMessage = 'Hurry up!';
  //   }else {
  //     this.type = 'danger';
  //     this.fullnessBarMessage = 'Sorry, all spots are busy!';
  //   }
  // }

  getParking(): void {
     const id = parseInt(this.route.snapshot.paramMap.get('id'));
     this.parkingService.getParking(id)
        .subscribe(parking => this.parking = parking);
  }

  getSpots(): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.parkingService.getSpotsByParkingId(id)
      .subscribe(spots => this.spots = spots);
  }

}
