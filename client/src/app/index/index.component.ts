import {ChangeDetectorRef, Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {ParkingListFilterComponent} from './parking-list-filter/parking-list-filter.component';
import {ParkingService} from '../parking.service';
import {MatDrawer, MatProgressBar, MatSnackBar} from '@angular/material';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Parking} from '../model/view/parking';
import {ParkingMapComponent} from './parking-map/parking-map.component';
import {StatisticsService} from '../statistic/statistics.service';
import {DataserviceService} from './dataservice.service';

const MiToKm = 1.60934;
const numberOfDaysByDefault = 30;

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

    @ViewChild('parkingMap') private parkingMap: ParkingMapComponent;

    @ViewChild('filter') private filter: ParkingListFilterComponent;

    @ViewChild('progressbar') private progressBar: MatProgressBar;

    @ViewChild('drawer') sideBar: MatDrawer;

    parkings: Parking[] = [];
    bestParkings: Parking[] = [];

    private progressBarVisible: boolean = false;
    private progressBarColor: string = 'primary';
    private progressBarMode: string = 'query';

    screenWidth: number;

    minPrice: number = 0;
    maxPrice: number = 0;
    hasCharger: boolean = false;

    constructor(private parkingService: ParkingService,
                private changeDetector: ChangeDetectorRef,
                private statisticService: StatisticsService,
                private dataService: DataserviceService,
                private snackBar: MatSnackBar) {
        this.screenWidth = window.innerWidth;
    }

    @HostListener('window:resize', ['$event'])
    onResize(event) {
        if (event.target.innerWidth < 800) {
            this.sideBar.close();
        }
        if (event.target.innerWidth > 800) {
            this.sideBar.open();
        }
    }

    ngOnInit() {

    }

    public search() {
        this.subscribeDataServiceValues();
        this.showLoadingProgressBar();
        this.parkingMap.lat = this.filter.locationField.value.latitude;
        this.parkingMap.lng = this.filter.locationField.value.longitude;
        this.parkingMap.radius = this.filter.radiusField.value * 1000;
        this.parkingMap.clearDirection();
        this.parkingMap.infoWindowOpened = null;
        this.parkingService.getParkingsNearby(this.parkingMap.lat, this.parkingMap.lng, this.parkingMap.radius).subscribe((response) => {
            this.hideProgressBar();
            this.parkings = response.body;
            this.filterParkings();
            this.pushValuesFromFilterToDataService(this.filter.priceRangeField.minControl.value,
                this.filter.priceRangeField.maxControl.value, this.filter.chargerField.value);
        }, error => {
            console.log(error);
            this.showErrorProgressBar();
        });
        setTimeout(() => this.findBestParkingsByLocation(
            this.parkingMap.lat,
            this.parkingMap.lng,
            this.parkingMap.radius,
            numberOfDaysByDefault,
            this.minPrice,
            this.maxPrice,
            this.hasCharger), 100);
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

    private filterParkings() {
        this.parkingMap.parkings = this.parkings.filter(parking => {
            let filter = this.filter.value;
            var distance = require('google-distance-matrix');

            var latLngOrigin = this.parkingMap.lat + ',' + this.parkingMap.lng;
            var lanLngDest = parking.latitude + ',' + parking.longitude;
            var origins = [latLngOrigin];
            var destinations = [lanLngDest];

            distance.key('AIzaSyAufS5bcmpO5UiWxG_MpcSOrIiRNzbUJus');
            distance.units('imperial');

            distance.matrix(origins, destinations, function (err, distances) {
                if (err) {
                    return console.log(err);
                }
                if (!distances) {
                    return console.log('no distances');
                }
                if (distances.status == 'OK') {
                    for (var i = 0; i < origins.length; i++) {
                        for (var j = 0; j < destinations.length; j++) {
                            var origin = distances.origin_addresses[i];
                            var destination = distances.destination_addresses[j];
                            if (distances.rows[0].elements[j].status == 'OK') {
                                let distance = distances.rows[i].elements[j].distance.text;
                                distance = distance.substr(0, distance.indexOf(' '));
                                Number.parseInt(distance);
                                distance *= MiToKm;
                                parking.distance = Math.floor(distance * 10) / 10;
                            } else {
                                console.log(destination + ' is not reachable by land from ' + origin);
                            }
                        }
                    }
                }
            });

            return parking.distance <= filter.radius * 1000
                && ((filter.priceRange.min) ? parking.price >= filter.priceRange.min : true)
                && ((filter.priceRange.max) ? parking.price <= filter.priceRange.max : true)
                && ((filter.hasCharger) ? parking.hasCharger == filter.hasCharger : true);
        });

        this.dataService.pushParkingsToDataService(this.parkingMap.parkings);
        this.refreshComponentView();

    }

    private refreshComponentView(): void {
        this.changeDetector.detectChanges();
        setTimeout(() => this.changeDetector.detectChanges(), 1);
    }

    findBestParkingsByLocation(latitude: number, longitude: number, radius: number, days: number, minPrice: number, maxPrice: number,
                               hasCharger: boolean) {
        this.statisticService.getBestParkingsByLocationPriceAndFunctional(latitude, longitude, radius, days, minPrice, maxPrice, hasCharger,
            false, false)
            .subscribe(bestParkiings => {
                this.bestParkings = bestParkiings;
                this.dataService.pushBestParkingsToDataService(this.bestParkings);
            });
    }

    subscribeDataServiceValues() {
        this.dataService.currentMinPrice.subscribe(minPrice => this.minPrice = minPrice);
        this.dataService.currentMaxPrice.subscribe(maxPrice => this.maxPrice = maxPrice);
        this.dataService.currentHasCharger.subscribe(hasCharger => this.hasCharger = hasCharger);
    }

    pushValuesFromFilterToDataService(minPrice: number, maxPrice: number, hasCharger: boolean) {
        if (minPrice == null) {
            minPrice = 0;
        }
        if (maxPrice == null) {
            maxPrice = 1000;
        }
        this.dataService.setMinPrice(minPrice);
        this.dataService.setMaxPrice(maxPrice);
        this.dataService.setHasCharger(hasCharger);
    }

    public isDisabled(): Boolean {
        if (this.filter.locationField.value === undefined || !this.filter.priceRangeField.minIsValid ||
            !this.filter.priceRangeField.maxIsValid) {
            return true;
        } else {
            return false;
        }
    }

}
