import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import {Location} from '@angular/common';

import {Parking} from '../model/view/parking';
import {ParkingService} from "../parking.service";
import {Spot} from '../model/view/spot';
import {SpotStatistic} from '../model/view/spotstatistic';
import {Observable} from 'rxjs/Observable';
import {NgbdDatepickerPopup} from '../datepicker-popup';





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
  goals = [];
  minDate: Date;
  maxDate: Date;
  

 constructor(
   private route: ActivatedRoute,
   private parkingService: ParkingService,
   private location: Location
 ) { 
  this.minDate = new Date();
  this.maxDate = new Date();

  

 }

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


 addItem() {
  this.goals.push(this.minDate.getDay()+"/"+this.minDate.getMonth()+"/"+this.minDate.getFullYear());
  this.goals.push(this.maxDate.getDay()+"/"+this.maxDate.getMonth()+"/"+this.maxDate.getFullYear());
}

}

