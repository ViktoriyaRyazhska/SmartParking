import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {ParkingListFilterComponent} from './parking-list-filter/parking-list-filter.component';
import {ParkingService} from '../parking.service';
import {ParkingListComponent} from './parking-list/parking-list.component';
import {MatProgressBar} from '@angular/material';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

    @ViewChild('parkingList') private parkingList: ParkingListComponent;

    @ViewChild('filter') private filter: ParkingListFilterComponent;

    @ViewChild('progressbar') private progressBar: MatProgressBar;

    private progressBarVisible: boolean = false;
    private progressBarColor: string = 'primary';
    private progressBarMode: string = 'query';

    constructor(private parkingService: ParkingService,
                private changeDetector: ChangeDetectorRef) {
    }

    ngOnInit() {
        this.filter.priceRange.subscribe(priceRange => {
            this.hideProgressBar();
        });
        this.filter.radius.subscribe(radius => {
            this.hideProgressBar();
        });
        this.filter.location.subscribe(location => {
            this.showLoadingProgressBar();
            this.parkingService.getParkingsNearby(location.latitude, location.longitude, this.filter.radiusMax * 1000).subscribe((response) => {
                this.hideProgressBar();
                this.parkingList.parkings = response.body;
            }, error => {
                console.log(error);
                this.showErrorProgressBar();
            });
        });
    }

    private showLoadingProgressBar() {
        this.progressBarVisible = true;
        this.progressBarColor = 'primary';
        this.progressBarMode = 'query';
    }

    private hideProgressBar() {
        this.progressBarVisible = false;
    }

    private showErrorProgressBar() {
        this.progressBarVisible = true;
        this.progressBarColor = 'warn';
        this.progressBarMode = 'determinate';
    }
}
