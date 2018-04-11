import {Component, OnInit} from '@angular/core';
import {StatisticsService} from "../statistics.service";
import {Parking} from "../../model/view/parking";

@Component({
    selector: 'app-parking-statistic',
    templateUrl: './parking-statistic.component.html',
    styleUrls: ['./parking-statistic.component.css']
})
export class ParkingStatisticComponent implements OnInit {

    parkings: Parking[];
    parkingsStreets: string[];
    parkingsCities: string[];
    selectedCity = 'Lviv';
    selectedStreet: string;
    selectedNumberOfDays = 7;
    days = [7, 14, 30, 365];
    calculatedDate = new Date();

    constructor(private statisticService: StatisticsService) {
    }

    ngOnInit() {
        this.findAllParkingsCities();
    }

    findBestParkings() {
        this.calculateDate();
        this.statisticService.getBestParkingsByCityStreetDate(this.selectedCity, this.selectedStreet, this.calculatedDate.getTime())
            .subscribe(parkings => {
                this.parkings = parkings;
            });
        this.refreshDate();
    }

    findParkingsStreetsFromInput(input: string) {
        this.statisticService.getParkingsStreetsByAnyMatching(this.selectedCity, input)
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

    clearCurrentData() {
        this.selectedStreet = null;
        this.parkings = null;
    }

    selectStreet(street: string) {
        this.selectedStreet = street;
    }

    calculateDate() {
        this.calculatedDate.setDate(new Date().getDate() - this.selectedNumberOfDays);
    }

    refreshDate() {
        this.calculatedDate = new Date();
    }


}
