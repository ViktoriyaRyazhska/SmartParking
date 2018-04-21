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
    public streetFilter = '';

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

}
