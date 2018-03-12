import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar-footer',
  templateUrl: './app-navbar-footer.component.html',
  styleUrls: ['./app-navbar-footer.component.css']
})
export class AppNavbarFooterComponent implements OnInit {
  navTitle = 'Filtering';

  constructor() { }

  ngOnInit() {
  }

}
