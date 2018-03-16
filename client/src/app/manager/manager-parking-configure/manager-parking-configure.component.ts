import {Component, OnInit} from '@angular/core';
import {Parking} from "../../model/view/parking";
import {ParkingService} from "../../parking.service";
import {ActivatedRoute} from '@angular/router';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
    selector: 'app-parking-configure',
    templateUrl: './manager-parking-configure.component.html',
    styleUrls: ['./manager-parking-configure.component.css']
})
export class ManagerParkingConfigureComponent implements OnInit {

    parking: Parking = new Parking();

    parkingForm = new FormGroup({
        address: new FormControl('', []),
        spotsCount: new FormControl('', [])
    });

    constructor(private route: ActivatedRoute,
                private parkingService: ParkingService) {
    }

    ngOnInit() {
        this.getParking();
    }

    getParking(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.parkingService.getParking(id)
            .subscribe(parking => this.parking = parking);
    }
}
