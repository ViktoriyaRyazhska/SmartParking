import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar-header',
  templateUrl: './app-navbar-header.component.html',
  styleUrls: ['./app-navbar-header.component.css']
})
export class AppNavbarHeaderComponent implements OnInit {

  navTitle = 'SmartParking';

  constructor() { }

  ngOnInit() {
  }

}
