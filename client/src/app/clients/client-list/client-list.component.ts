import {Component, OnInit} from '@angular/core';
import {ClientService} from "../client.service";
import {Client} from "../../model/view/client";

@Component({
    selector: 'app-client-list',
    templateUrl: './client-list.component.html',
    styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

    clients: Client[];
    searchText: string;
    backEndMode: boolean = true;

    constructor(private clientService: ClientService) {
    }

    ngOnInit() {
        this.getLimitClients();
    }

    getAllClients(): void {
        this.clientService.getClients()
            .subscribe(clients => this.clients = clients);
    }

    getLimitClients(): void {
        this.clientService.getLimitNumberOfClients()
            .subscribe(clients => this.clients = clients);
    }

    findClientsFromBackEnd(input: string): void {
        this.clientService.findClientsByAnyMatch(input)
            .subscribe(clients => this.clients = clients);
    }

    switchMode() {
        this.backEndMode = !this.backEndMode;
        if (!this.backEndMode) {
            this.getAllClients();
        }
    }

}
