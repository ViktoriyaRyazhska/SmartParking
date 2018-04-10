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
        this.findAllParkingsCities();
    }

    findBestParkingsByStreet(inputStreet: string) {
        this.statisticService.getBestParkingsByCityAndStreet(this.selectedCity, inputStreet)
            .subscribe(parkings => {
                this.parkings = parkings;
            });
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


}
