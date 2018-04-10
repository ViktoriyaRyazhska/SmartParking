import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import {Location} from '@angular/common';
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
  start_date:string;
  end_date:string;
 

  

 constructor(
   private route: ActivatedRoute,
   private parkingService: ParkingService,
   
  ) { 
  this.minDate = new Date();
  this.minDate.setDate(this.minDate.getDate()-7);
  this.maxDate = new Date();
  this.minMonth = this.minDate.getMonth()+1;
  this.maxMonth = this.maxDate.getMonth()+1;
  this.start_date = this.minDate.getDate()+"/"+ this.minMonth+"/"+this.minDate.getFullYear();
  this.end_date = this.maxDate.getDate()+"/"+this.maxMonth+"/"+this.maxDate.getFullYear();
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
  this.parkingService.getSpotStatistic(id,
    this.start_date,this.end_date)
    .subscribe(statistic => this.statistic = statistic);
}




 addItem() {
   
   if(this.minDate > this.maxDate)
   {
    this.tempDate = this.minDate;
    this.minDate = this.maxDate;
    this.maxDate = this.tempDate;
   }
 
 this.goals.push(this.start_date);  
 this.goals.push(this.end_date);
 }

}


