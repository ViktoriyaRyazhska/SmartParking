import {Component, OnInit} from '@angular/core';
import {Client} from "../../model/view/client";
import {ClientService} from "../client.service";
import {ActivatedRoute} from '@angular/router';
import {FormGroup, FormControl, FormArray, Validators, FormBuilder, NgForm} from '@angular/forms';
import {ClientRequest} from "./client-request";

@Component({
    selector: 'app-client-edit',
    templateUrl: './client-edit.component.html',
    styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent implements OnInit {

    id: number;
    client: ClientRequest;
    clientRequest: ClientRequest;

    constructor(private route: ActivatedRoute,
                private clientService: ClientService) {
    }

    ngOnInit() {
        this.getClientById();
    }

    getClientById(): void {
        const id = +parseInt(this.route.snapshot.paramMap.get('id'));
        this.clientService.getClientDetail(id)
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

    // setUpClientRequest(): void {
    //     this.clientRequest.firstName = this.client.firstName;
    //     this.clientRequest.lastName = this.client.lastName;
    //     this.clientRequest.email = this.client.email;
    //     this.clientRequest.provider = this.client.provider;
    //     this.clientRequest.role = this.client.role;
    // }

}


