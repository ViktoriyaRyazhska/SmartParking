import {Component, OnInit} from '@angular/core';
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {Client} from "../client";
import {ClientService} from "../client.service";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";

@Component({
    selector: 'app-client-edit',
    templateUrl: './client-edit.component.html',
    styleUrls: ['./client-edit.component.css']
})
export class ClientEditComponent implements OnInit {

    constructor() {
    }

    ngOnInit(): void {
      }

    name = 'Angular';
    characters = [
        'Finn the human',
        'Jake the dog',
        'Princess bubblegum',
        'Lumpy Space Princess',
        'Beemo1',
        'Beemo2'
    ]


}
