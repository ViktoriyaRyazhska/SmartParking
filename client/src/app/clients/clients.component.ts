import {Component, OnInit} from '@angular/core';
import {Client} from "./clients";
import {ClientService} from "./client.service";

@Component({
    selector: 'app-clients',
    templateUrl: './clients.component.html',
    styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

    constructor() {
    }

    ngOnInit() {
    }


}
