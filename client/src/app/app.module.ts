import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';

import {AppComponent} from './app.component';
import {AppNavbarHeaderComponent} from './app-navbar-header/app-navbar-header.component';
import {ParkingsComponent} from './parkings/parkings.component';
import {ParkingService} from './parking.service';
import {AppNavbarFooterComponent} from './app-navbar-footer/app-navbar-footer.component';
import {GeoLocationService} from './geo-location.service';

import {AppRoutingModule} from './app-routing.module';
import {SuperuserConfigurationComponent} from './superuser-configuration/superuser-configuration.component';
import { ParkingDetailComponent } from './parking-detail/parking-detail.component';
import {ProviderService} from "./providers/provider.service";
import { ClientsComponent } from './clients/clients.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import { ClientEditComponent } from './clients/client-edit/client-edit.component';
import { ClientService } from './clients/client.service';
import { ClientItemComponent } from './clients/client-list/client-item/client-item.component';
import { ProviderListComponent } from './providers/provider-list/provider-list.component';
import {LoginComponent} from "./auth/login/login.component";
import {RegistrationComponent} from "./auth/registration/registration.component";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AngularMaterialsModule} from "./angular-materials.module";

@NgModule({
    declarations: [
        AppComponent,
        AppNavbarHeaderComponent,
        ParkingsComponent,
        AppNavbarFooterComponent,
        SuperuserConfigurationComponent,
        ParkingDetailComponent,
        SuperuserConfigurationComponent,
        ClientsComponent,
        ClientListComponent,
        ClientEditComponent,
        ClientItemComponent,
        ProviderListComponent,
        ProviderListComponent,
        LoginComponent,
        RegistrationComponent
    ],
    imports: [
        HttpClientModule,
        BrowserModule,
        RouterModule,
        NgbModule.forRoot(),
        AppRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        AngularMaterialsModule
    ],
    providers: [
        ParkingService,
        GeoLocationService,
        ProviderService,
        ClientService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
