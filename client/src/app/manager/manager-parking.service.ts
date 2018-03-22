import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {environment} from '../../environments/environment';
import {Parking} from '../model/view/parking';

@Injectable()
export class ManagerParkingService {

    // TODO Change url to manager-configuration/parking/{id}

    private parkingConfigureUrl = environment.apiUrl + '/manager-parkings-configure';

    constructor(private http: HttpClient) {
    }

    getParking(id: number): Observable<Parking> {
        return this.http.get<Parking>(this.parkingConfigureUrl + '/' + id).map(json => {
            return Parking.copyOf(json);
        });
    }

    getParkings(): Observable<HttpResponse<Parking[] | any>> {
        return this.http.get<HttpResponse<Parking[] | any>>(
            'http://localhost:8080/manager-configuration/parkings', {observe: 'response'});
    }

    saveParking(parking: Parking): Observable<HttpResponse<any>> {
        return this.http.post<HttpResponse<any>>(
            this.parkingConfigureUrl + '/save', parking, {observe: 'response'});
    }
}
