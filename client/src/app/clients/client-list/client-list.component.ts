import {Component, OnInit} from '@angular/core';
import {ClientService} from "../client.service";
import {Client} from "../../model/view/client";
import {ActivatedRoute} from "@angular/router";
import {PagerService} from "../../_services/pager.service";
import 'rxjs/add/operator/map'
import {Observable} from "rxjs/Observable";

@Component({
    moduleId: module.id,
    selector: 'app-client-list',
    templateUrl: './client-list.component.html',
    styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

    clients: Client[];

    // pager object
    pager: any = {};

    // paged items
    pagedClientItems: Client[];

    constructor(private clientService: ClientService,
                private route: ActivatedRoute,
                private pagerService: PagerService) {
    }

    ngOnInit() {
        const input = (this.route.snapshot.paramMap.get('input'));
        if (input != null) {
            this.findClientsFromBackEnd(input);
        } else
        this.getLimitNumberOfClients();
    }

    setPage(page: number) {
        if (page < 1 || page > this.pager.totalPages) {
            return;
        }

        if (this.clients.length > 0) {
            // get pager object from service
            this.pager = this.pagerService.getPager(this.clients.length, page);

            // get current page of items
            this.pagedClientItems = this.clients.slice(this.pager.startIndex, this.pager.endIndex + 1);
        }
        else this.pagedClientItems = this.clients;
    }

    getAllClients(): void {
        this.clientService.getClients()
            .subscribe(clients => {
                this.clients = clients;
                this.setPage(1);
            });
    }

    getLimitNumberOfClients(): void {
        this.clientService.getLimitNumberOfClients()
            .subscribe(clients => {
                this.clients = clients;
                this.setPage(1);
            });
    }

    findClientsFromBackEnd(searchInput: string): void {
        this.clientService.findClientsByAnyMatch(searchInput)
            .subscribe(clients => {
                this.clients = clients;
                this.setPage(1);
            });

    }

    findDrivers(): void {
        this.clientService.findClientsByAnyMatch("0")
            .subscribe(clients => {
                this.clients = clients;
                this.setPage(1);
            });
    }

    findProviderManagers(): void {
        this.clientService.findClientsByAnyMatch("1")
            .subscribe(clients => {
                this.clients = clients;
                this.setPage(1);
            });
    }

    findSuperusers(): void {
        this.clientService.findClientsByAnyMatch("2")
            .subscribe(clients => {
                this.clients = clients;
                this.setPage(1);
            });
    }


}
