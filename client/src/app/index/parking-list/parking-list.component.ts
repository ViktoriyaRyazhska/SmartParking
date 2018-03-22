import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {MapsAPILoader} from '@agm/core';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/debounceTime';
import {Parking} from '../../model/view/parking';

@Component({
    selector: 'app-parking-list',
    templateUrl: './parking-list.component.html',
    styleUrls: ['./parking-list.component.css']
})
export class ParkingListComponent implements OnInit, OnDestroy {

    public parkings: Parking[];

    constructor(private mapsAPILoader: MapsAPILoader,
                private changeDetector: ChangeDetectorRef) {
    }

    ngOnInit(): void {
    }


    ngOnDestroy(): void {
    }

    // locationControl: FormControl = new FormControl();
    //
    // public options: [ 'One', 'Two', 'Three'];
    //
    // public latitude: number;
    // public longitude: number;
    // public searchControl: FormControl;
    // public zoom: number;
    //
    // @ViewChild("search")
    // public searchElementRef: ElementRef;
    //
    // constructor(private mapsAPILoader: MapsAPILoader,
    //             private ngZone: NgZone) {
    // }
    //
    // ngOnInit() {
    //     //set google maps defaults
    //     this.zoom = 4;
    //     this.latitude = 39.8282;
    //     this.longitude = -98.5795;
    //
    //     //create search FormControl
    //     this.searchControl = new FormControl();
    //
    //     //set current position
    //     this.setCurrentPosition();
    //
    //     //load Places Autocomplete
    //     /*this.mapsAPILoader.load().then(() => {
    //         let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
    //             types: ["address"]
    //         });
    //         autocomplete.addListener("place_changed", () => {
    //             this.ngZone.run(() => {
    //                 //get the place result
    //                 let place: google.maps.places.PlaceResult = autocomplete.getPlace();
    //
    //                 //verify  result
    //                 if (place.geometry === undefined || place.geometry === null) {
    //                     return;
    //                 }
    //
    //                 //set latitude, longitude and zoom
    //                 this.latitude = place.geometry.location.lat();
    //                 this.longitude = place.geometry.location.lng();
    //                 this.zoom = 12;
    //             });
    //
    //             /*let request = <google.maps.places.AutocompletionRequest> {
    //                 input: "Lviv",
    //                 location: new google.maps.LatLng(0, 0),
    //                 radius: 0
    //             };
    //             let service = new google.maps.places.AutocompleteService();
    //             service.getPlacePredictions(request, (stutus) => {
    //                 console.log(stutus);
    //             });
    //         });
    //     });*/
    // }
    //
    // private setCurrentPosition() {
    //     if ("geolocation" in navigator) {
    //         navigator.geolocation.getCurrentPosition((position) => {
    //             this.latitude = position.coords.latitude;
    //             this.longitude = position.coords.longitude;
    //             this.zoom = 12;
    //         });
    //     }
    // }

    // placeSearch: any;
    // autocomplete: any;
    // componentForm = {
    //     street_number: 'short_name',
    //     route: 'long_name',
    //     locality: 'long_name',
    //     administrative_area_level_1: 'short_name',
    //     country: 'long_name',
    //     postal_code: 'short_name'
    // };
    //
    // public map: google. = null;
    //
    // parkings: ParkingItem[] = Array(0);
    // filteredParkings: ParkingItem[] = Array(0);
    //
    // latitude: number = 0;
    // longitude: number = 0;
    //
    // constructor(private parkingService: ParkingService,
    //             private geoLocation: GeoLocationService) {
    // }
    //
    // ngOnInit() {
    //     this.getParkings();
    // }
    //
    // ngAfterViewInit() {
    //     this.initAutocomplete();
    // }
    //
    // getParkings(): void {
    //     this.parkingService.getParkings()
    //         .subscribe(parkings => {
    //             this.parkings = parkings;
    //             this.filteredParkings = parkings;
    //         });
    //     this.geoLocation.getLocation()
    //         .subscribe((position: Position) => {
    //             this.latitude = position.coords.latitude;
    //             this.longitude = position.coords.longitude;
    //             this.filteredParkings = [];
    //             this.filterByGeoLocation();
    //         });
    // }
    //
    // getDistanceFromLatLonInKm(currentLatitude: number, currentLongitude: number): boolean {
    //     if (this.latitude === 0 || this.longitude === 0) {
    //         return true;
    //     }
    //     let R = 6371; // Radius of the earth in km
    //     let dLat = this.deg2rad(currentLatitude - this.latitude);  // deg2rad below
    //     let dLon = this.deg2rad(currentLongitude - this.longitude);
    //     let a =
    //         Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    //         Math.cos(this.deg2rad(currentLatitude)) * Math.cos(this.deg2rad(this.latitude)) *
    //         Math.sin(dLon / 2) * Math.sin(dLon / 2)
    //     ;
    //     let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    //     let d = R * c; // Distance in km
    //
    //     return d < 10;
    // }
    //
    // deg2rad(deg) {
    //     return deg * (Math.PI / 180)
    // }
    //
    // filterByGeoLocation() {
    //     this.parkings.forEach((parking) => {
    //         if (this.getDistanceFromLatLonInKm(parking.longitude, parking.latitude)) {
    //             this.filteredParkings.push(parking);
    //         }
    //     });
    // }
    //
    // initAutocomplete() {
    //     // Create the autocomplete object, restricting the search to geographical
    //     // location types.
    //     this.autocomplete = new  google.maps.places.Autocomplete(
    //         /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
    //         {types: ['geocode']});
    //
    //     // When the user selects an address from the dropdown, populate the address
    //     // fields in the form.
    //     this.autocomplete.addListener('place_changed', this.fillInAddress);
    // }
    //
    // fillInAddress() {
    //     // Get the place details from the autocomplete object.
    //     let place = this.autocomplete.getPlace();
    //
    //     for (let component in this.componentForm) {
    //         document.getElementById(component).value = '';
    //         document.getElementById(component).disabled = false;
    //     }
    //
    //     // Get each component of the address from the place details
    //     // and fill the corresponding field on the form.
    //     for (let i = 0; i < place.address_components.length; i++) {
    //         let addressType = place.address_components[i].types[0];
    //         if (this.componentForm[addressType]) {
    //             let val = place.address_components[i][this.componentForm[addressType]];
    //             document.getElementById(addressType).value = val;
    //         }
    //     }
    // }
    //
    // // Bias the autocomplete object to the user's geographical location,
    // // as supplied by the browser's 'navigator.geolocation' object.
    // geolocate() {
    //     if (navigator.geolocation) {
    //         navigator.geolocation.getCurrentPosition(function (position) {
    //             let geolocation = {
    //                 lat: position.coords.latitude,
    //                 lng: position.coords.longitude
    //             };
    //             let circle = new Circle({
    //                 center: geolocation,
    //                 radius: position.coords.accuracy
    //             });
    //             this.autocomplete.setBounds(circle.getBounds());
    //         });
    //     }
    // }
}
