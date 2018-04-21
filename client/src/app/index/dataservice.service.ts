import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Parking} from "../model/view/parking";
import {Observable} from "rxjs/Observable";

@Injectable()
export class DataserviceService {

    private parkings: BehaviorSubject<Parking[]> = new BehaviorSubject([]);
    currentParkings : Observable<Parking[]> = this.parkings.asObservable();

    constructor() {
    }

    pushParkingsToDataService(parkings: Parking[]) {
        this.parkings.next(parkings);
    }

}
