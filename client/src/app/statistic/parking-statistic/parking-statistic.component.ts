import {Component, OnInit} from '@angular/core';
import {StatisticsService} from "../statistics.service";
import {ParkingsInfo} from "../parkingsinfo";
import {ActivatedRoute} from "@angular/router";
import {FormControl} from "@angular/forms";
import {moment} from "ngx-bootstrap/chronos/test/chain";

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
    selectedNumberOfDays = 7;
    days = [7, 14, 30, 365];
    calculatedDate = new Date();

    constructor(private statisticService: StatisticsService, private router: ActivatedRoute) {
    }

    ngOnInit() {
        this.findAllParkingsCities();
    }

    findBestParkingsByStreet(inputStreet: string) {

        this.statisticService.getBestParkingsByCityAndStreet(this.selectedCity, inputStreet, this.calculatedDate.getTime())
            .subscribe(parkings => {
                this.parkings = parkings;
            });
        this.refreshDate();
    }

    findParkingsStreets(input: string) {
        this.statisticService.getParkingsStreet(this.selectedCity, input)
            .subscribe(parkingsStreets => {
                this.parkingsStreets = parkingsStreets;
            });
    }

    findAllParkingsCities() {
        this.statisticService.getAllParkingsCities()
            .subscribe(parkingsCities => {
                this.parkingsCities = parkingsCities;
            });
    }

    findParkingsCities(value: string) {
        this.statisticService.getParkingsCitiesByAnyMatching(value)
            .subscribe(parkingsCities => {
                this.parkingsCities = parkingsCities;
            });
    }

    theCityWasSelected() {
        this.selectedStreet = "";
    }

    theStreetWasSelected(street: string) {
        this.selectedStreet = street;
    }

    calculateDate() {
        this.calculatedDate.setDate(new Date().getDate() - this.selectedNumberOfDays);
    }

    refreshDate() {
        this.calculatedDate = new Date();
    }


}
