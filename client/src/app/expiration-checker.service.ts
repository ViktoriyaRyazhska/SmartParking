import { Injectable } from '@angular/core';
import {
    HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor,
    HttpRequest
} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {TokenStorage} from "./auth/token/token-storage";
import {environment} from "../environments/environment";
import {Token} from "./auth/token/token";

@Injectable()
export class ExpirationCheckerService implements HttpInterceptor{
    private refreshUrl = environment.apiUrl + '/auth/refresh';

  constructor(private http: HttpClient,
              private tokenStorage: TokenStorage) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      console.log('Go into interceptor 1');
      if(this.tokenStorage.isExpired()) {
          let temp = new Token(this.tokenStorage.getToken());
          console.log('Token is expired ' + temp.token);
          this.tokenStorage.signOut();
          console.log('Signed out ' + this.tokenStorage.getToken());
          this.refreshToken(temp);
      }
      console.log('Exit from interceptor 1');
      return next.handle(req);
    }

    private refreshToken(token: Token){
      console.log('Try to refresh');
      console.log('Current token' + token.token);
      this.http.post(this.refreshUrl, token).subscribe((token: Token) => {
          console.log('Resieved token ' + token.token);
          this.tokenStorage.saveToken(token.token);
      });
      console.log('Token saved ' + this.tokenStorage.getToken());
    }
}
