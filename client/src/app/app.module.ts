import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';


import {AppComponent} from './app.component';
import {AppNavbarHeaderComponent} from './app-navbar-header/app-navbar-header.component';
import {ParkingsComponent} from './parkings/parkings.component';
import {ParkingService} from './parking.service';
import {AppNavbarFooterComponent} from './app-navbar-footer/app-navbar-footer.component';
import {GeoLocationService} from './geo-location.service';


import {AppRoutingModule} from './/app-routing.module';
import {SuperuserConfigurationComponent} from './superuser-configuration/superuser-configuration.component';
import {ProviderService} from "./provider.service";


@NgModule({
    declarations: [
        AppComponent,
        AppNavbarHeaderComponent,
        ParkingsComponent,
        AppNavbarFooterComponent,
        SuperuserConfigurationComponent
    ],
    imports: [
        HttpClientModule,
        BrowserModule,
        RouterModule,
        NgbModule.forRoot(),
        AppRoutingModule
    ],
    providers: [
        ParkingService,
        GeoLocationService,
        ProviderService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
