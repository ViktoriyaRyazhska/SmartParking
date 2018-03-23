import { Component, OnInit } from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {TokenStorage} from '../auth/login/token-storage';

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

  hasToke(): boolean{
    return TokenStorage.hasToken();
  }

  logOut() {
    TokenStorage.signOut();
  }


}
