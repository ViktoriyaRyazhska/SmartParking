import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";
import {Parking} from "../../model/view/parking";
import {StatisticsService} from "../../statistic/statistics.service";
import {DataserviceService} from "../dataservice.service";

@Component({
    selector: 'app-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

    mobileQuery: MediaQueryList;
    private _mobileQueryListener: () => void;

    parkings: Parking[];
    mostPopularParkings: Parking[];
    public streetFilter = '';

    showMostPopular: boolean = false;

    constructor(changeDetectorRef: ChangeDetectorRef,
                media: MediaMatcher,
                private statisticService: StatisticsService,
                private dataService: DataserviceService) {
        this.mobileQuery = media.matchMedia('(max-width: 600px)');
        this._mobileQueryListener = () => changeDetectorRef.detectChanges();
        this.mobileQuery.addListener(this._mobileQueryListener);
    }

    ngOnDestroy(): void {
        this.mobileQuery.removeListener(this._mobileQueryListener);
    }

    ngOnInit() {
        this.dataService.currentParkings.subscribe(parkings => this.parkings = parkings);
    }

    showMostPopularParkings() {
        this.showMostPopular = true;
        this.dataService.currentBestParkings.subscribe(parkings => this.mostPopularParkings = parkings);
    }

    showAllParkings() {
        this.showMostPopular = false;
        this.dataService.currentParkings.subscribe(parkings => this.parkings = parkings);
    }


}
