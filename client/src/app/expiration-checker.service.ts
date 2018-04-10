import { Injectable } from '@angular/core';
import {
    HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor,
    HttpRequest, HttpResponse
} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {TokenStorage} from "./auth/token/token-storage";
import {TokenPair} from "./auth/token/token-pair";
import {Router} from "@angular/router";

@Injectable()
export class ExpirationCheckerService implements HttpInterceptor{

    constructor(private http: HttpClient,
                private router: Router,
                private tokenStorage: TokenStorage) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log('Go into interceptor 1');
        if(this.tokenStorage.isExpired()) {
            req = req.clone({
                headers: req.headers.append('Refresh-token', this.tokenStorage.getRefreshToken())
            });
        }
        console.log('Exit from interceptor 1');
        return next.handle(req).do(response => {
                if(response instanceof HttpResponse) {
                    if(response.headers.get('Access-token') != null && response.headers.get('Refresh-token') != null) {
                        this.tokenStorage.saveToken(new TokenPair(response.headers.get('Access-token'), response.headers.get('Refresh-token')));
                    }
                }
                return response;
            },
            error => {
                if(error instanceof HttpErrorResponse) {
                    if(error.status == 401){
                        this.tokenStorage.signOut();
                        this.router.navigate(['/']);
                    }
                }
            });
    }
}
