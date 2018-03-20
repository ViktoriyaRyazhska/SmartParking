import {NgModule} from "@angular/core";

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule, MatInputModule, MatIconModule, MatButtonModule} from '@angular/material';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSelectModule} from '@angular/material/select';


@NgModule({
    imports: [
        BrowserAnimationsModule,
        NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatSlideToggleModule,
        MatSelectModule
    ],
    exports: [
        BrowserAnimationsModule,
        NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatSlideToggleModule,
        MatSelectModule
    ]
})
export class AngularMaterialsModule {
}
