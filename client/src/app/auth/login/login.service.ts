import { Injectable } from '@angular/core';
import {LoginData} from "./login-data";
import {Observable} from "rxjs/Observable";

@Injectable()
export class LoginService {

  constructor() { }

  signIn(loginData: LoginData): Observable<any> {

  }

}
