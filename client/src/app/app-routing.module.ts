import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ParkingsComponent} from './parkings/parkings.component';
import {SuperuserConfigurationComponent} from './superuser-configuration/superuser-configuration.component';
import {ClientsComponent} from './clients/clients.component';
import {ProviderListComponent} from './providers/provider-list/provider-list.component';

const routes: Routes = [
    {path: '', redirectTo: '/index', pathMatch: 'full'},
    {path: 'index', component: ParkingsComponent},
    {path: 'configuration', component: SuperuserConfigurationComponent},
    {path: 'configuration/clientlist', component: ClientsComponent},
    {path: 'configuration/providers', component: ProviderListComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
