import { Component, OnInit } from '@angular/core';
import {TokenStorage} from '../auth/token/token-storage';
import {Role} from "../auth/roles";

@Component({
    selector: 'app-navbar-header',
    templateUrl: './app-navbar-header.component.html',
    styleUrls: ['./app-navbar-header.component.css']
})
export class AppNavbarHeaderComponent implements OnInit {
    role: any = Role;

  navTitle = 'SmartParking';

  constructor(private tokenStorage: TokenStorage) { }

  ngOnInit() {
  }

  getRole(): Role {
    return this.tokenStorage.getRole();
  }

  hasToken(): boolean{
    return this.tokenStorage.hasToken();
  }

  logOut() {
      this.tokenStorage.signOut();
  }


}
