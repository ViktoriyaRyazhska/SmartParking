
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {v4 as uuid} from 'uuid';
import {MatSnackBar} from '@angular/material';

import {Parking} from '../../model/view/parking';
import {ManagerParkingService} from '../manager-parking.service';
import {HttpResponse} from '@angular/common/http';

@Component({
    selector: 'app-manager-parking-configure',
    templateUrl: './manager-parking-configure.component.html',
    styleUrls: ['./manager-parking-configure.component.css']
})
export class ManagerParkingConfigureComponent implements OnInit {

    configureType: ConfigureType;
    geoType: GeoType;
    switch: Switch;
    step = -1;

    loadedParking: Parking;
    parking: Parking;

    parkingConfigureForm = new FormGroup({
        city: new FormControl('', [
            Validators.required,
        ]),
        street: new FormControl('', [Validators.required,]),
        building: new FormControl('', [Validators.required,]),
        latitude: new FormControl('', [Validators.required,]),
        longitude: new FormControl('', [Validators.required,]),
        price: new FormControl('', [Validators.required,]),
        token: new FormControl('', [Validators.required,]),
        providerName: new FormControl('', []),
        favoritesCount: new FormControl('', []),
        spotsCount: new FormControl('', [])
    });

    constructor(private route: ActivatedRoute,
                public snackBar: MatSnackBar,
                private formBuilder: FormBuilder,
                private managerParkingService: ManagerParkingService) {
    }

    ngOnInit() {
        this.switch  = new  Switch('off', SwitchType.OFF);
        if (this.route.snapshot.paramMap.get('configureType') === 'edit') {
            this.configureType = new ConfigureType('edit', ManagerParkingConfigureType.EDIT);
            this.loadParking();
        } else {
            this.configureType = new ConfigureType('add', ManagerParkingConfigureType.ADD);
            this.loadedParking = new Parking();
            this.parking = new Parking();
        }
    }

    loadParking(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.managerParkingService.getParking(id)
            .subscribe(parking => {
                this.loadedParking = parking;
                this.parking = parking.clone();
            });
    }

    saveParking(): void {
            this.managerParkingService.saveParking(this.parking)
                .subscribe((response: HttpResponse<any>) => {
                if (this.configureType.type === ManagerParkingConfigureType.ADD) {
                    this.snackBar.open('Parking created sucsessfully.', null, {
                        duration: 2000
                    });
                } else {
                    this.snackBar.open('Parking updated sucsessfully.', null, {
                        duration: 2000
                    });
                }
            }, error => {
                this.snackBar.open('Cannot save parking.', null, {
                    duration: 2000
                });
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
        this.parking.city = this.loadedParking.city;
        this.parking.street = this.loadedParking.street;
        this.parking.building = this.loadedParking.building;
    }

    resetLocation() {
        this.parking.latitude = this.loadedParking.latitude;
        this.parking.longitude = this.loadedParking.longitude;
    }

    resetPrice() {
        this.parking.price = this.loadedParking.price;
    }

    resetToken() {
        this.parking.token = this.loadedParking.token;
    }

    addressClick(): void {
          this.geoType = new GeoType('address', ManagerParkingGeoType.ADDRESS);
          this.switch  = new  Switch('on', SwitchType.ON);
          console.log(this.geoType.text);
          var geocoder =  new google.maps.Geocoder();
          var address = "1045 mission street san francisco";
              var address2 = "132 Городоцька Львів";
              
                
          
               var result = "";
          
               geocoder.geocode({ 'address': address2 }, (results, status) => {
                 var latitude = results[0].geometry.location.lat();
                var longitude = results[0].geometry.location.lng();
                 console.log("lat: " + latitude + ", long: " + longitude);
              });
          
          
              

    }
 
    locationClick(): void {
        this.geoType = new GeoType('location', ManagerParkingGeoType.LOCATION);
        this.switch  = new  Switch('on', SwitchType.ON);
        console.log(this.geoType.text);
        var latlng = new google.maps.LatLng(49.843489, 24.032726);
        var geocoder =  new google.maps.Geocoder();
        geocoder.geocode({'location': latlng},(results, status) => {
            var address:string[] = results[0].formatted_address.split(" ");
          console.log(address);
           });  
 }

setParkingLocation() : void {
    var geocoder =  new google.maps.Geocoder();
      var  address = ""+this.parking.building+" "+this.parking.street+" "+this.parking.city;
                                       var result = "";
                 geocoder.geocode({ 'address': address }, (results, status) => {
                 var latitude = results[0].geometry.location.lat();
                var longitude = results[0].geometry.location.lng();
                this.parking.latitude = latitude;
                this.parking.longitude = longitude;
                 console.log("lat: " + latitude + ", long: " + longitude);
              }); 
}


setParkingAddress() : void {
    var latlng = new google.maps.LatLng(this.parking.latitude, this.parking.longitude);
                var geocoder =  new google.maps.Geocoder();
                geocoder.geocode({'location': latlng},(results, status) => {
                    var address:string[] = results[0].formatted_address.split(" ");
                  console.log(address);
                  this.parking.building = address[2];
                  this.parking.street = address[1];
                  this.parking.city = address[3];
                   });  
}





}

export enum ManagerParkingConfigureType {
    EDIT, ADD
}

class ConfigureType {
    constructor(public text: string, public type: ManagerParkingConfigureType) {
    }
}


export enum ManagerParkingGeoType {
    ADDRESS, LOCATION
}

class GeoType {
    constructor(public text: string, public type: ManagerParkingGeoType) {
    }
}


export enum SwitchType {
    OFF, ON
}

class Switch {
    constructor(public text: string, public type: SwitchType) {
    }
}





