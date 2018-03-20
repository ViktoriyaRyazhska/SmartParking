import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

import {Parking} from './model/view/parking';
import {ParkingItem} from "./model/view/parking-item";
import {Spot} from './model/view/spot';

@Injectable()
export class ParkingService {

    private parkingUrl = 'http://localhost:8080/parkings/';
    private parkingDetailUrl = 'http://localhost:8080/parkingdetail/';

    constructor(private http: HttpClient) {
    }

    getParkings(): Observable<ParkingItem[]> {
        return this.http.get<ParkingItem[]>(this.parkingUrl);
    }

    getParking(id: number): Observable<Parking> {
        return this.http.get<Parking>('http://localhost:8080/parkingdetail/' + id);
    }

    getSpotsByParkingId(id: number): Observable<Spot[]> {
        return this.http.get<Spot[]>(this.parkingDetailUrl + id + '/spots');
    }
}
