import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpResponse} from '@angular/common/http';

import {Parking} from './model/view/parking';
import {ParkingItem} from './model/view/parking-item';
import {Spot} from './model/view/spot';

@Injectable()
export class ParkingService {

    private parkingUrl = 'http://localhost:8080/parkings/';
    private parkingDetailUrl = 'http://localhost:8080/parkingdetail/';

    constructor(private http: HttpClient) {
    }

    getParkings(latitude: number, longitude: number): Observable<HttpResponse<ParkingItem[] | any>> {
        return this.http.get<HttpResponse<ParkingItem[] | any>>(this.parkingUrl, {
            params: {
                latitude: latitude.toString(),
                longitude: longitude.toString(),
            }
        });
    }

    getParking(id: number): Observable<Parking> {
        return this.http.get<Parking>(this.parkingDetailUrl + id);
    }

    getSpotsByParkingId(id: number): Observable<Spot[]> {
        return this.http.get<Spot[]>(this.parkingDetailUrl + id + '/spots');
    }

    getAvailableSpotsByParkingId(id: number): Observable<Spot[]> {
        return this.http.get<Spot[]>(this.parkingDetailUrl + id + '/freespots');
    }

}
