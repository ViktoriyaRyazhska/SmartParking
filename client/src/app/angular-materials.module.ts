import {NgModule} from '@angular/core';

import {BrowserAnimationsModule,} from '@angular/platform-browser/animations';
import {
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatSnackBarModule,
    MatTabsModule,
    MatToolbarModule,
} from '@angular/material';



@NgModule({
    imports: [
        BrowserAnimationsModule,
        // NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatToolbarModule,
        MatTabsModule,
        MatSelectModule,
        MatCardModule,
        MatSnackBarModule,
    ],
    exports: [
        BrowserAnimationsModule,
        // NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatButtonModule,
        MatToolbarModule,
        MatTabsModule,
        MatSelectModule,
        MatCardModule,
        MatSnackBarModule,
    ]
})
export class AngularMaterialsModule {
}