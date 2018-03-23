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
}
