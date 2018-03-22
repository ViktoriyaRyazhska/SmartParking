import {Component, OnInit, ViewChild} from '@angular/core';
import {ParkingListFilterComponent} from './parking-list-filter/parking-list-filter.component';
import {ParkingService} from '../parking.service';

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

    @ViewChild('filter') private filter: ParkingListFilterComponent;

    constructor(private parkingService: ParkingService) {
    }

    ngOnInit() {
        this.filter.location.subscribe(location => {
            this.parkingService.getParkings(location.latitude, location.longitude).subscribe((response) => {
                console.log(response);
            });
        });
    }

}
