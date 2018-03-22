import {Component, OnInit} from '@angular/core';
import {TokenStorage} from "../auth/login/token-storage";
import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {ParkingListFilterComponent} from './parking-list-filter/parking-list-filter.component';
import {ParkingService} from '../parking.service';
import {ParkingListComponent} from './parking-list/parking-list.component';

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

    @ViewChild('parkingList') private parkingList: ParkingListComponent;
    @ViewChild('filter') private filter: ParkingListFilterComponent;

    constructor(private parkingService: ParkingService,
                private changeDetector: ChangeDetectorRef) {
    }

    ngOnInit() {

        this.filter.location.subscribe(location => {
            this.parkingService.getParkings(location.latitude, location.longitude).subscribe((response) => {
                console.log(response);
                this.parkingList.parkings = response.body;

                this.changeDetector.detectChanges();
                setTimeout(() => this.changeDetector.detectChanges(), 1);
            });
        });
    }

}
