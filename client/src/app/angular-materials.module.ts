import {NgModule} from "@angular/core";

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule, MatInputModule, MatIconModule, MatButtonModule} from '@angular/material';


@NgModule({
  imports: [
      BrowserAnimationsModule,
      NoopAnimationsModule,
      MatFormFieldModule,
      MatInputModule,
      MatIconModule,
      MatButtonModule
  ],
  exports: [
      BrowserAnimationsModule,
      NoopAnimationsModule,
      MatFormFieldModule,
      MatInputModule,
      MatIconModule,
      MatButtonModule
  ]
})
export class AngularMaterialsModule { }
