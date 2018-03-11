import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { Parking } from '../parking';
import { PARKINGS } from '../mock-parkings';
import { ParkingService } from '../parking.service';

@Component({
  selector: 'app-parkings',
  templateUrl: './parkings.component.html',
  styleUrls: ['./parkings.component.css']
})
export class ParkingsComponent implements OnInit {

  parkings: Parking[];

  constructor(private parkingService: ParkingService) { }

  ngOnInit() {
    this.getParkings();
  }

  getParkings(): void {
    this.parkingService.getParkings()
      .subscribe(parkings => this.parkings = parkings);
  }

}
