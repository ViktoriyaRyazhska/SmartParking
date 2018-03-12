import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ParkingsComponent} from './parkings/parkings.component';

const routes: Routes = [
  {path: '', redirectTo: '/index', pathMatch: 'full' },
  {path: 'index', component: ParkingsComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
