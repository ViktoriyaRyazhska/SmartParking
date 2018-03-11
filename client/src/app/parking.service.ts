import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

import { Parking } from './parking';
import { PARKINGS } from './mock-parkings';

@Injectable()
export class ParkingService {

  constructor() { }

  getParkings(): Observable<Parking[]>{
    return of(PARKINGS);
  }

}
