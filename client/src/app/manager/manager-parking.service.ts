import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

import {Parking} from "../model/view/parking";

@Injectable()
export class ManagerParkingService {

    private parkingConfigureUrl = "http://localhost:8080/manager-parkings-configure";

    constructor(private http: HttpClient) {
    }

    getParking(id: number): Observable<Parking> {
        return this.http.get<Parking>(this.parkingConfigureUrl + "/" + id);
    }

    updateParking(parking: Parking) {
        return this.http.post(this.parkingConfigureUrl + '/update', parking);
    }
}
