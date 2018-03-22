import {Component, OnInit} from '@angular/core';
import {Parking} from '../../model/view/parking';
import {ManagerParkingService} from '../manager-parking.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-manager-parking-list',
    templateUrl: './manager-parking-list.component.html',
    styleUrls: ['./manager-parking-list.component.css']
})
export class ManagerParkingListComponent implements OnInit {

    parkings: Parking[];

    constructor(private managerParkingService: ManagerParkingService,
                private router: Router) {
    }

    ngOnInit() {
        this.loadParkings();
    }

    loadParkings(): void {
        this.managerParkingService.getParkings()
            .subscribe(parkings => {
                this.parkings = parkings.body;
            });
        // TODo Catch errors
    }

    showOnMap(latitude: number, longitude: number): void {
        window.open(`https://www.google.com/maps/search/?api=1&query=
                    ${latitude},${longitude}`);
    }

    onParkingDeleteClick(parking: Parking): void {

    }
}
