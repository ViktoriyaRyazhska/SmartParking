import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { AppComponent } from './app.component';
import { AppNavbarHeaderComponent } from './app-navbar-header/app-navbar-header.component';
import { ParkingsComponent } from './parkings/parkings.component';
import { ParkingService } from './parking.service';
import { AppNavbarFooterComponent } from './app-navbar-footer/app-navbar-footer.component';


@NgModule({
  declarations: [
    AppComponent,
    AppNavbarHeaderComponent,
    ParkingsComponent,
    AppNavbarFooterComponent
  ],
  imports: [
    BrowserModule,
    NgbModule.forRoot()
  ],
  providers: [
    ParkingService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
