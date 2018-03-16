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

  constructor(
    private route: ActivatedRoute,
    private parkingService: ParkingService,
    private location: Location
  ) { }

  ngOnInit() {
    this.getParking();
    this.getSpots();
  }

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
