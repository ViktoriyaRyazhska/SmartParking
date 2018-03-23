import {NgModule} from '@angular/core';

import {BrowserAnimationsModule,} from '@angular/platform-browser/animations';
import {
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatProgressBarModule,
    MatSelectModule,
    MatSnackBarModule,
    MatTabsModule,
    MatToolbarModule,
} from '@angular/material';


@NgModule({
    imports: [
        BrowserAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatToolbarModule,
        MatTabsModule,
        MatSelectModule,
        MatCardModule,
        MatSnackBarModule,
        MatListModule,
        MatProgressBarModule
    ],
    exports: [
        BrowserAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatToolbarModule,
        MatTabsModule,
        MatSelectModule,
        MatCardModule,
        MatSnackBarModule,
        MatListModule,
        MatProgressBarModule
    ]
})
export class AngularMaterialsModule {
}