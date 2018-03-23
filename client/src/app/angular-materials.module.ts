import {NgModule} from '@angular/core';

import {BrowserAnimationsModule,} from '@angular/platform-browser/animations';
import {
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSelectModule,
    MatSnackBarModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
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
        MatTooltipModule,
        MatDialogModule,
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
        MatTooltipModule,
        MatDialogModule,
    ]
})
export class AngularMaterialsModule {
}