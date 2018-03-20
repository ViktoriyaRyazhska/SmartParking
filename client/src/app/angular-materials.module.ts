import {NgModule} from "@angular/core";

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatTabsModule
} from '@angular/material';


@NgModule({
    imports: [
        BrowserAnimationsModule,
        NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatToolbarModule,
        MatTabsModule
    ],
    exports: [
        BrowserAnimationsModule,
        NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatToolbarModule,
        MatTabsModule
    ]
})
export class AngularMaterialsModule {
}