import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {ManagerParkingResponse} from "./manager-parking-response";

@Injectable()
export class ManagerParkingService {

    private parkingConfigureUrl = "http://localhost:8080/manager-parkings-configure";

    constructor(private http: HttpClient) {
    }

    getParking(id: number): Observable<ManagerParkingResponse> {
        return this.http.get<ManagerParkingResponse>(this.parkingConfigureUrl + "/" + id);
    }
}
