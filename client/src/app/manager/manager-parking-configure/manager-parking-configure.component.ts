import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Component, OnInit} from '@angular/core';

import {ManagerParkingService} from "../manager-parking.service";
import {Parking} from "../../model/view/parking";

@Component({
    selector: 'app-manager-parking-configure',
    templateUrl: './manager-parking-configure.component.html',
    styleUrls: ['./manager-parking-configure.component.css']
})
export class ManagerParkingConfigureComponent implements OnInit {

    parking: Parking;

    parkingConfigureForm = new FormGroup({
        addressRegion: new FormControl('', []),
        addressCity: new FormControl('', []),
        addressStreet: new FormControl('', []),
        addressBuildingNumber: new FormControl('', []),
        locationLatitude: new FormControl('', []),
        locationLongitude: new FormControl('', []),
        price: new FormControl('', []),
        token: new FormControl('', []),
        providerName: new FormControl('', []),
        favoritesCount: new FormControl('', []),
        spotsCount: new FormControl('', [])
    });

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

    updateParking(): void {
        // this.parking = this.parkingConfigureForm.value;
        this.managerParkingService.updateParking(this.parking)
            .subscribe(response => {

            });
    }
}
