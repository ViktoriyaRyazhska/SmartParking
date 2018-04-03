import {Component, OnInit} from '@angular/core';
import {Parking} from "../../model/view/parking";
import {StatisticsService} from "../statistics.service";
import {Parkingstat} from "../parkingstat";

@Component({
    selector: 'app-parking-statistic',
    templateUrl: './parking-statistic.component.html',
    styleUrls: ['./parking-statistic.component.css']
})
export class ParkingStatisticComponent implements OnInit {

    parkings: Parkingstat[];

    constructor(private statisticService: StatisticsService) {
    }

    ngOnInit() {
        this.findAllParkings();
    }

    findAllParkings(): void {
        this.statisticService.getAllParkings()
            .subscribe(parkings => {
                this.parkings = parkings;
            });
    }

}
