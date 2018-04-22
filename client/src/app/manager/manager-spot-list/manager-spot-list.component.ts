import {Component, OnInit} from '@angular/core';
import * as HttpStatus from 'http-status-codes';
import {Spot} from "../../model/view/spot";
import {ManagerSpotService} from "../manager-spot.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog, MatSnackBar} from "@angular/material";
import {DeleteConfirmationDialogComponent} from "../manager-parking-list/delete-confirmation-dialog/delete-confirmation-dialog.component";
import {HttpResponse} from "@angular/common/http";
import {SpotAddDialogComponent} from "./spot-add-dialog/spot-add-dialog.component";

@Component({
    selector: 'app-manager-spot-list',
    templateUrl: './manager-spot-list.component.html',
    styleUrls: ['./manager-spot-list.component.css']
})
export class ManagerSpotListComponent implements OnInit {

    spots: Spot[] = [];
    spot: Spot;

    constructor(private route: ActivatedRoute,
                private managerSpotService: ManagerSpotService,
                private router: Router,
                private snackBar: MatSnackBar,
                private dialog: MatDialog) {
    }


    ngOnInit() {
        this.loadSpots();
    }

    loadSpots(): void {
        const parkingId = parseInt(this.route.snapshot.paramMap.get('id'));
        this.managerSpotService.getSpots(parkingId)
            .subscribe(spots => {
                this.spots = spots.body;
            });
    }

    onSpotEditClick(spot: Spot): void {
//        this.router.navigateByUrl('/manager-configuration/parkings/edit/' + id);
    }

    onSpotDeleteClick(spot: Spot): void {
        let dialogRef = this.dialog.open(DeleteConfirmationDialogComponent, {
            data: {confirmed: false}
        });

        dialogRef.afterClosed().subscribe(data => {
            if (data.confirmed) {
                this.managerSpotService.deleteSpot(spot)
                    .subscribe(response => this.onDeleteResponse(spot, response));
            }
        });
    }

    onSpotAddClick(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        let dialogRef = this.dialog.open(SpotAddDialogComponent, {
            data: new Spot()
        });

        dialogRef.afterClosed().subscribe((data: Spot) => {
            if (data.isFree) {
                data.parkingId = id;
                this.managerSpotService.saveSpot(data).subscribe((response: HttpResponse<any>) => {
                    this.snackBar.open('Spot created sucsessfully.', null, {
                        duration: 2000
                    });
                });
                this.loadSpots();
            }
        });

    }

    private onDeleteResponse(spot: Spot, response: HttpResponse<any>): void {
        if (response.status === HttpStatus.OK) {
            this.snackBar.open('Spot deleted sucsessfully.', null, {
                duration: 2000
            });
            let index = this.spots.indexOf(spot);
            this.spots.splice(index, 1);
        }
    }
}
