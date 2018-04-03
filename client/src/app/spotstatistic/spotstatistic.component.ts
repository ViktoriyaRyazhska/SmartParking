import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import {Location} from '@angular/common';

import {Parking} from '../model/view/parking';
import {ParkingService} from "../parking.service";
import {Spot} from '../model/view/spot';
import {SpotStatistic} from '../model/view/spotstatistic';
import {Observable} from 'rxjs/Observable';





@Component({
  selector: 'app-spotstatistic',
  templateUrl: './spotstatistic.component.html',
  styleUrls: ['./spotstatistic.component.css']
})
export class SpotstatisticComponent implements OnInit {

 
  statistic: SpotStatistic[];

  type: String;
  fullnessBarMessage: String;
  max: number;
  value:number;
  thirtySecInterval: number = 30000;
  favoriteNameInputHide: boolean = true;
  

 constructor(
   private route: ActivatedRoute,
   private parkingService: ParkingService,
   private location: Location
 ) { }

 ngOnInit() {
   
    
   this.getSpotStatistic();
   setInterval(this.refresh(), this.thirtySecInterval);
 }

 refresh(): void{
      this.getSpotStatistic();
 }


 getSpotStatistic(): void{
   const id = parseInt(this.route.snapshot.paramMap.get('id'));
   this.parkingService.getSpotStatistic(id)
     .subscribe(statistic => this.statistic = statistic);
 }

}
