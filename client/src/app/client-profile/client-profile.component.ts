import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {ClientService} from "../clients/client.service";
import {Client} from "../model/view/client";
import {Provider} from "../model/view/provider";

@Component({
    selector: 'app-client-profile',
    templateUrl: './client-profile.component.html',
    styleUrls: ['./client-profile.component.css']
})
export class ClientProfileComponent implements OnInit {

    client: Client;
    provider: Provider;
    showProviderDetails: boolean = false;


    constructor(private route: ActivatedRoute,
                private clientService: ClientService,
                private router: Router) {
    }


    ngOnInit() {
//      this.getClientById(TokenStorage.getUserId());
    }

    getClientById(id: number): void {
        this.clientService.getClientDetail(id)
            .subscribe(client => this.client = client);
    }


    seeProviderDetails() {
        this.showProviderDetails = !this.showProviderDetails;
    }


    goToClientEditForm() {
        this.router.navigate(['configuration/clients/edit/', this.client.id]);
    }

    goToProvidersDetails() {
        this.router.navigate(['configuration/providers/', this.client.providersId]);
    }

}
