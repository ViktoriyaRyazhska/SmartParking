import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import { Parking } from './parking';
import { PARKINGS } from './mock-parkings';

@Injectable()
export class ParkingService {
  // private parkingUrl = 'assets/test.json';
  private parkingUrl = 'http://localhost:8080/p';

  constructor(private http: HttpClient) { }

  /** Log a HeroService message with the MessageService */


  getParkings(): Observable<Parking[]>{
    return this.http.get<Parking[]>(this.parkingUrl);
  }

}
