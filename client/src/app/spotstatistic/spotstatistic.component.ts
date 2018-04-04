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

 
  thirtySecInterval: number = 30000;
  favoriteNameInputHide: boolean = true;
  goals = [];
  minDate: Date;
  maxDate: Date;
  tempDate: Date;
  minMonth: number;
  maxMonth: number;

  

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
   if(this.minDate > this.maxDate)
   {
    this.tempDate = this.minDate;
    this.minDate = this.maxDate;
    this.maxDate = this.tempDate;
   }
  this.minMonth = this.minDate.getMonth()+1;
  this.goals.push(this.minDate.getDate()+"/"+ this.minMonth+"/"+this.minDate.getFullYear());
  this.maxMonth = this.maxDate.getMonth()+1;
  this.goals.push(this.maxDate.getDate()+"/"+this.maxMonth+"/"+this.maxDate.getFullYear());
 
}

}


