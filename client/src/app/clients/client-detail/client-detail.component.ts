import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ClientService} from "../client.service";
import {Client} from '../client';

@Component({
    selector: 'app-client-detail',
    templateUrl: './client-detail.component.html',
    styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit {
    client: Client;

    constructor(private route: ActivatedRoute,
                private clientService: ClientService) {
    }

    ngOnInit() {
        this.getClientById();
    }

    getClientById(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.clientService.getClientDetail(id)
            .subscribe(client => this.client = client);
    }

}
