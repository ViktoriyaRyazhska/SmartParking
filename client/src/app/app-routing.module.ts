import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParkingsComponent} from './parkings/parkings.component';
import {SuperuserConfigurationComponent} from "./superuser-configuration/superuser-configuration.component";
import {ParkingDetailComponent} from './parking-detail/parking-detail.component';
import {ClientsComponent} from "./clients/clients.component";
import {ProviderListComponent} from "./providers/provider-list/provider-list.component";
import {LoginComponent} from "./auth/login/login.component";
import {ProviderDetailComponent} from "./providers/provider-detail/provider-detail.component";
import {RegistrationComponent} from "./auth/registration/registration.component";

const routes: Routes = [
    {path: '', redirectTo: '/index', pathMatch: 'full'},
    {path: 'index', component: ParkingsComponent},
    {path: 'configuration', component: SuperuserConfigurationComponent},
    {path: 'parkingdetail/:id', component: ParkingDetailComponent},
    {path: 'configuration', component: SuperuserConfigurationComponent,},
    {path: 'configuration/clientlist', component: ClientsComponent},
    {path: 'configuration/providers', component: ProviderListComponent},
    {path: 'login', component: LoginComponent},
    {path: 'registration', component: RegistrationComponent},
    {path: 'configuration/providers/:id', component: ProviderDetailComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
