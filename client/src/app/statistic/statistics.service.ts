import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {ParkingsInfo} from "./parkingsinfo";
import {Observable} from 'rxjs/Observable';
import {environment} from '../../environments/environment';

@Injectable()
export class StatisticsService {

    private statisticUrl = environment.apiUrl + '/statistic';

    constructor(private http: HttpClient) {
    }

    getParkingsStreetsByAnyMatching(city: string, street: string): Observable<string[]> {
        return this.http.get<string[]>(this.statisticUrl + '/findparkingstreets', {
            params: {
                city: city,
                street: street
            }
        });
    }

    getParkingsCitiesByAnyMatching(input: string): Observable<string[]> {
        return this.http.get<string[]>(this.statisticUrl + '/findparkingscities/' + input);
    }

    getAllParkingsCities(): Observable<string[]> {
        return this.http.get<string[]>(this.statisticUrl + '/findallparkingscities');
    }

    getBestParkingsByCityStreetDate(city: string, street: string, date: number): Observable<ParkingsInfo[]> {
        return this.http.get<ParkingsInfo[]>(this.statisticUrl + '/findbestparkings', {
            params: {
                city: city,
                street: street,
                date: date.toString()
            }
        });
    }

}
