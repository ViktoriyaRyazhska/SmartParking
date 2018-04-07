import {Component, OnInit} from '@angular/core';
import {Parking} from "../../model/view/parking";
import {StatisticsService} from "../statistics.service";
import {ParkingsInfo} from "../parkingsinfo";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-parking-statistic',
    templateUrl: './parking-statistic.component.html',
    styleUrls: ['./parking-statistic.component.css']
})
export class ParkingStatisticComponent implements OnInit {

    parkings: ParkingsInfo[];
    parkingsStreets: string[];
    parkingsCities: string[];
    selectedCity = 'Lviv';
    selectedStreet: string;

    constructor(private statisticService: StatisticsService, private router: ActivatedRoute) {
    }

    ngOnInit() {
        // this.findAllParkings();
    }

    findAllParkings(): void {
        this.statisticService.getAllParkings()
            .subscribe(parkings => {
                this.parkings = parkings;
            });
    }

    findMostPopularProvidersByStreet(value: string) {
        this.statisticService.getAllParkingsByCity(value)
            .subscribe(parkings => {
                this.parkings = parkings;
            });
    }

    findBestParkingsByStreet(value: string) {
        this.selectedStreet = value;
        this.statisticService.getBestParkingsByStreet(value)
            .subscribe(parkings => {
                this.parkings = parkings;
            });
    }

    findParkingsStreets(value: string) {
        this.statisticService.getParkingsStreet(value)
            .subscribe(parkingsStreets => {
                this.parkingsStreets = parkingsStreets;
            });
    }

    findParkingsCities(value: string) {
        this.statisticService.getParkingsCities(value)
            .subscribe(parkingsCities => {
                this.parkingsCities = parkingsCities;
            });
    }

    selectCity(value: string) {
        this.selectedCity = value;
    }

    selectStreet(value: string) {
        this.selectedStreet = value;
    }


}
