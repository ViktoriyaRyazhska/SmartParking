import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {RegistrationData} from "./registration/registration-data";
import {Observable} from "rxjs/Observable";
import {LoginData} from "./login/login-data";

/*const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Basic ' + btoa(angularClient.clientID + ':' + angularClient.clientSecret)
    })
};*/

@Injectable()
export class AuthService {

  private loginUrl = environment.apiUrl + '/auth/generate-token';
  private registrationUrl = environment.apiUrl + '/auth/signup';

  constructor(private http: HttpClient) { }

  signIn(loginData: LoginData): Observable<any> {
    return this.http.post(this.loginUrl, loginData);
  }

  register(data: RegistrationData): Observable<any> {
    return this.http.post(this.registrationUrl, data);
  }
}
