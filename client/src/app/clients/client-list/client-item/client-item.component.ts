import {Component, Input, OnInit} from '@angular/core';
import {Client} from "../../client";

@Component({
  selector: 'app-client-item',
  templateUrl: './client-item.component.html',
  styleUrls: ['./client-item.component.css']
})
export class ClientItemComponent implements OnInit {
    @Input() client: Client;
    @Input() index: number;

  constructor() { }

  ngOnInit() {
  }

}
