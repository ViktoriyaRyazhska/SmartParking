import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Component, OnInit} from '@angular/core';

import {ManagerParkingService} from '../manager-parking.service';
import {Parking} from '../../model/view/parking';
import {v4 as uuid} from 'uuid';

@Component({
    selector: 'app-manager-parking-configure',
    templateUrl: './manager-parking-configure.component.html',
    styleUrls: ['./manager-parking-configure.component.css']
})
export class ManagerParkingConfigureComponent implements OnInit {

    step = -1;

    gettedParking: Parking;
    parking: Parking;

    parkingConfigureForm = new FormGroup({
        city: new FormControl('', []),
        street: new FormControl('', []),
        building: new FormControl('', []),
        latitude: new FormControl('', []),
        longitude: new FormControl('', []),
        price: new FormControl('', []),
        token: new FormControl('', []),
        providerName: new FormControl('', []),
        favoritesCount: new FormControl('', []),
        spotsCount: new FormControl('', [])
    });

    constructor(private route: ActivatedRoute,
                private router: Router,
                private formBuilder: FormBuilder,
                private managerParkingService: ManagerParkingService) {
    }

    ngOnInit() {
        this.getParking();
    }

    getParking(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.managerParkingService.getParking(id)
            .subscribe(parking => {
                this.gettedParking = parking;
                this.parking = parking.clone();
            });
        // TODo Catch errors
    }

    saveParking(): void {
        this.managerParkingService.saveParking(this.parking)
            .subscribe(response => {
                console.log('Response: ' + response)
                // TODO Write response handler
            });
    }

    setStep(index: number): void {
        this.step = index;
    }

    nextStep(): void {
        this.step++;
    }

    prevStep(): void {
        this.step--;
    }

    showOnMap(): void {
        window.open(`https://www.google.com/maps/search/?api=1&query=
                    ${this.parking.latitude},${this.parking.longitude}`);
    }

    generateToken() {
        this.parking.token = uuid();
    }

    resetAddress() {
        this.parking.city = this.gettedParking.city;
        this.parking.street = this.gettedParking.street;
        this.parking.building = this.gettedParking.building;
    }

    resetLocation() {
        this.parking.latitude = this.gettedParking.latitude;
        this.parking.longitude = this.gettedParking.longitude;
    }

    resetPrice() {
        this.parking.price = this.gettedParking.price;
    }

    resetToken() {
        this.parking.token = this.gettedParking.token;
    }
}
