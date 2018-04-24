import {Component, OnInit} from '@angular/core';
import {ClientService} from "../client.service";
import {ActivatedRoute, Router} from '@angular/router';
import {Role} from "../role";
import {Client} from "../../model/view/client";
import {Provider} from "../../model/view/provider";
import {MatSnackBar} from '@angular/material';

@Component({
    selector: 'app-client-edit',
    templateUrl: './client-edit.component.html',
    styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent implements OnInit {

    id: number;
    client: Client;
    provider: Provider;
    providers: Provider[] = [];
    roles: Role[] = [];

    constructor(private route: ActivatedRoute,
                private clientService: ClientService,
                private router: Router,
                private snackBar: MatSnackBar) {
    }

    ngOnInit() {
        this.getClientById();
        this.getRoles();
        this.getProviders();
    }

    getClientById(): void {
        const id = +parseInt(this.route.snapshot.paramMap.get('id'));
        this.id = id;
        this.clientService.getClientDetail(id)
            .subscribe(client => this.client = client);
    }

    getProviders(): void {
        this.clientService.getProviders()
            .subscribe(providers => this.providers = providers);
    }

    updateClientById(): void {

        if (this.client.role == 'PROVIDER_MANAGER' && this.client.providersId == 0) {
            this.snackBar.open('Please, select some provider', null, {
                duration: 4000
            });
        } else {
            if (this.client.role == 'DRIVER' || this.client.role == 'SUPERUSER') {
                this.client.providersId = 0;
                this.client.providerName = 'no provider';
            }
            this.clientService.updateClient(this.id, this.client)
                .subscribe(data => {
                    this.snackBar.open('Client has been updated successfully', null, {
                        duration: 4000
                    });
                });
        }
    }

    goToClientList() {
        this.router.navigate(['configuration/clients']);
    }

    getRoles(): void {
        this.roles = this.clientService.getRoles();
    }

    updateProviderName(selectedName: string) {
        this.client.providerName = selectedName;
    }

}