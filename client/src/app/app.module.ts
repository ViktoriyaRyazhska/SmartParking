import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {MAT_LABEL_GLOBAL_OPTIONS, MatButtonModule, MatDividerModule, MatExpansionModule} from '@angular/material';

import {CommonModule} from '@angular/common';

import {AppComponent} from './app.component';
import {AppNavbarHeaderComponent} from './app-navbar-header/app-navbar-header.component';
import {ParkingListComponent} from './index/parking-list/parking-list.component';
import {ParkingService} from './parking.service';
import {GeoLocationService} from './geo-location.service';
import {ManagerParkingService} from './manager/manager-parking.service';

import {AppRoutingModule} from './app-routing.module';
import {SuperuserConfigurationComponent} from './superuser-configuration/superuser-configuration.component';
import {ParkingDetailComponent} from './parking-detail/parking-detail.component';
import {ProviderService} from './superuser-configuration/providers/provider.service';
import {ClientsComponent} from './clients/clients.component';
import {ClientListComponent} from './clients/client-list/client-list.component';
import {ClientEditComponent} from './clients/client-edit/client-edit.component';
import {ClientService} from './clients/client.service';
import {ClientItemComponent} from './clients/client-list/client-item/client-item.component';
import {ProviderListComponent} from './superuser-configuration/providers/provider-list/provider-list.component';
import {ProviderDetailComponent} from './superuser-configuration/providers/provider-detail/provider-detail.component';
import {AddProviderComponent} from './superuser-configuration/providers/add-provider/add-provider.component';
import {FilterPipe} from './clients/client-list/filter.pipe';
import {ClientDetailComponent} from './clients/client-detail/client-detail.component';
import {LoginComponent} from './auth/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RegistrationComponent} from './auth/registration/registration.component';
import {AngularMaterialsModule} from './angular-materials.module';
import {IndexComponent} from './index/index.component';
import {ParkingListFilterComponent} from './index/parking-list-filter/parking-list-filter.component';
import {MatSelectModule, MatAutocompleteModule } from "@angular/material";
import {ManagerParkingConfigureComponent} from './manager/manager-parking-configure/manager-parking-configure.component';
import {ManagerParkingListComponent} from './manager/manager-parking-list/manager-parking-list.component';
import {AgmCoreModule} from '@agm/core';


@NgModule({
    declarations: [
        IndexComponent,
        AppComponent,
        AppNavbarHeaderComponent,
        ParkingListComponent,
        SuperuserConfigurationComponent,
        ParkingDetailComponent,
        SuperuserConfigurationComponent,
        ClientsComponent,
        ClientListComponent,
        ClientEditComponent,
        ClientItemComponent,
        ProviderListComponent,
        LoginComponent,
        RegistrationComponent,
        ProviderDetailComponent,
        ParkingListFilterComponent,
        ManagerParkingConfigureComponent,
        ManagerParkingListComponent,
        AddProviderComponent,
        FilterPipe,
        ClientDetailComponent
    ],
    imports: [
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyDLIMvbPlry-zu4nLaSaYeAKW7Xjgum74I',
            libraries: ['places']
        }),
        HttpClientModule,
        BrowserModule,
        RouterModule,
        NgbModule.forRoot(),
        AppRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        AngularMaterialsModule,
        MatSelectModule,
        CommonModule,
        MatAutocompleteModule,
        AngularMaterialsModule,
        MatDividerModule,
        MatExpansionModule,
        MatButtonModule
    ],
    providers: [
        ParkingService,
        ManagerParkingService,
        GeoLocationService,
        ProviderService,
        ClientService,
    ],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
