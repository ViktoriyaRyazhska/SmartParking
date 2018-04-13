import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {ParkingService} from "../../parking.service";
import {SpotStatistic} from '../../model/view/spotstatistic';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpErrorResponse, HttpResponse, HttpParams} from '@angular/common/http';
import {Chart} from 'chart.js';


@Component({
    selector: 'app-spotstatistic',
    templateUrl: './spotstatistic.component.html',
    styleUrls: ['./spotstatistic.component.css']
})
export class SpotstatisticComponent implements OnInit {


    statistic: SpotStatistic[];


    thirtySecInterval: number = 30000;
    favoriteNameInputHide: boolean = true;
    minDate: Date;
    maxDate: Date;
    startTime: Date;
    endTime: Date;
    tempDate: Date;
    minMonth: number;
    maxMonth: number;

    start_date: string;
    end_date: string;
    hoursChart = [];
    eventsChart = [];
    numbers: number[];
    hours: number[];
    events: number[];


    constructor(private route: ActivatedRoute,
                private router: Router,
                private parkingService: ParkingService,) {
        this.minDate = new Date();
        this.minDate.setDate(this.minDate.getDate() - 7);
        this.maxDate = new Date();
        this.minMonth = this.minDate.getMonth() + 1;
        this.maxMonth = this.maxDate.getMonth() + 1;


    }


    ngOnInit() {


        this.getSpotStatistic();
        this.fillArraysToGraphic();
        setInterval(this.refresh(), this.thirtySecInterval);

    }

    refresh(): void {
        this.getSpotStatistic();
    }


    fillArraysToGraphic(): void {
        this.numbers = []
        this.statistic.forEach((element) => {
            this.numbers.push(element.id);
        });
        this.hours = []
        this.statistic.forEach((element) => {
            this.hours.push(element.numberOfHours);
        });
        this.hours.push(0);
        this.events = []
        this.statistic.forEach((element) => {
            this.events.push(element.numberOfEvents);
        });
        this.events.push(0);


    }

    drawHourGraphic(): void {
        var densityData = {
            label: 'Spots',
            data: this.hours
        };


        this.hoursChart = new Chart('canvas', {
            type: 'bar',
            data: {
                labels: this.numbers,
                datasets: [densityData]
            },
            options: {
                legend: {
                    display: true
                },
                title: {
                    display: true,
                    text: 'How many hours spots of this parking have been taken'
                },
                scales: {
                    xAxes: [{
                        display: true
                    }],
                    yAxes: [{
                        display: true
                    }],
                }
            }
        });

        this.numbers = null;
        this.hours = null;
        this.events = null;

    }


    drawEventGraphic(): void {

        var densityData = {
            label: 'Spots',
            data: this.events
        };


        this.eventsChart = new Chart('canvas', {
            type: 'bar',
            data: {
                labels: this.numbers,
                datasets: [densityData]
            },
            options: {
                legend: {
                    display: true,
                    text: 'Whi'
                },
                title: {
                    display: true,
                    text: 'Which spots are most popular'
                },
                scales: {
                    xAxes: [{
                        display: true
                    }],
                    yAxes: [{
                        display: true
                    }],
                }
            }
        });

        this.numbers = null;
        this.hours = null;
        this.events = null;

    }

    getSpotStatistic(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.router.navigate(['parkingdetail/' + id + '/spotstatistic'],
            {
                queryParams: {
                    start_day: this.minDate.getDate(),
                    start_month: this.minDate.getMonth(),
                    start_year: this.minDate.getFullYear(),
                    end_day: this.maxDate.getDate(),
                    end_month: this.maxDate.getMonth(),
                    end_year: this.maxDate.getFullYear()
                }
            }
        );
        this.startTime = new Date();
        this.endTime = new Date();
        this.startTime.setDate(this.route.snapshot.queryParams["start_day"]);
        this.startTime.setMonth(this.route.snapshot.queryParams["start_month"]);
        this.startTime.setFullYear(this.route.snapshot.queryParams["start_year"]);
        this.endTime.setDate(this.route.snapshot.queryParams["end_day"]);
        this.endTime.setMonth(this.route.snapshot.queryParams["end_month"]);
        this.endTime.setFullYear(this.route.snapshot.queryParams["end_year"]);
        console.log('this.minDate =' + this.startTime.toString());
        console.log('this.maxDate =' + this.endTime.toString());
        this.parkingService.getSpotStatistic(id,
            this.minDate.getTime().toString(), this.maxDate.getTime().toString())
            .subscribe(statistic => this.statistic = statistic);
        /*  this.parkingService.getSpotStatistic(id,
            this.startTime.getTime().toString(), this.endTime.getTime().toString())
            .subscribe(statistic => this.statistic = statistic);*/

    }


    addItem() {

        if (this.minDate > this.maxDate) {
            this.tempDate = this.minDate;
            this.minDate = this.maxDate;
            this.maxDate = this.tempDate;
        }

        this.getSpotStatistic();


    }


    showHoursGraphic() {
        this.fillArraysToGraphic();
        this.drawHourGraphic();
    }


    showEventsGraphic() {
        this.fillArraysToGraphic();
        this.drawEventGraphic();
    }

    returnToParkingDetail() {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.router.navigate(['parkingdetail/' + id]);
    }


}


