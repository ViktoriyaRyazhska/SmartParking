import {ActivatedRoute} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {Component, OnInit} from '@angular/core';

import {ManagerParkingService} from "../manager-parking.service";
import {ManagerParkingResponse} from "../manager-parking-response";

@Component({
    selector: 'app-manager-parking-configure',
    templateUrl: './manager-parking-configure.component.html',
    styleUrls: ['./manager-parking-configure.component.css']
})
export class ManagerParkingConfigureComponent implements OnInit {

    parking: ManagerParkingResponse;

    constructor(private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private managerParkingService: ManagerParkingService) {
    }

    ngOnInit() {
        this.getParking();
    }

    getParking(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.managerParkingService.getParking(id)
            .subscribe(parking => this.parking = parking);
    }
}
