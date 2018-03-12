import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { AppComponent } from './app.component';
import { AppNavbarHeaderComponent } from './app-navbar-header/app-navbar-header.component';
import { ParkingsComponent } from './parkings/parkings.component';
import { ParkingService } from './parking.service';
import { AppNavbarFooterComponent } from './app-navbar-footer/app-navbar-footer.component';
import { GeoLocationService } from './geo-location.service';
import { AppRoutingModule } from './/app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    AppNavbarHeaderComponent,
    ParkingsComponent,
    AppNavbarFooterComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    NgbModule.forRoot(),
    AppRoutingModule
  ],
  providers: [
    ParkingService,
    GeoLocationService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
