import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Parkingstat} from "./parkingstat";
import {Observable} from 'rxjs/Observable';
import {environment} from '../../environments/environment';

@Injectable()
export class StatisticsService {

    private statisticUrl = environment.apiUrl+ '/statistic';

    constructor(private http: HttpClient) {
    }

    getAllParkings(): Observable<Parkingstat[]> {
        return this.http.get<Parkingstat[]>(this.statisticUrl + '/allparkings');
    }

}
