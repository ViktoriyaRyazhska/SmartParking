import {Component, OnInit} from '@angular/core';
import {ClientService} from "../client.service";
import {ActivatedRoute} from '@angular/router';
import {ClientRequest} from "../client-request";

@Component({
    selector: 'app-client-edit',
    templateUrl: './client-edit.component.html',
    styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent implements OnInit {

    id: number;
    client: ClientRequest;

    constructor(private route: ActivatedRoute,
                private clientService: ClientService) {
    }

    ngOnInit() {
        this.getClientById();
    }

    getClientById(): void {
        const id = +parseInt(this.route.snapshot.paramMap.get('id'));
        this.clientService.getClientDetailToEdit(id)
            .subscribe(client => this.client = client);
    }

    onUpdate() {
        this.udateClientById();
    }

    udateClientById(): void {
        const id = +parseInt(this.route.snapshot.paramMap.get('id'));
        this.clientService.updateClient(id, this.client)
            .subscribe(data => {
                alert('Client was updated successfully.');
            });
    }

}


