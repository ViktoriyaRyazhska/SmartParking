import {Injectable} from '@angular/core';
import {
    HttpClient,
    HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
    HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import {TokenStorage} from './auth/token/token-storage';
import {Router} from "@angular/router";

@Injectable()
export class InterceptorService implements HttpInterceptor {

    constructor(private tokenStorage: TokenStorage) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log('Go into interceptor 2');
        if (request.url.startsWith('http://localhost:8080/') && this.tokenStorage.getAccessToken()) {
            request = this.addAuthHeaderToRequest(request);
        }
        console.log('Exit from interceptor 2');
        return next.handle(request);
    }

    private addAuthHeaderToRequest(request: HttpRequest<any>): HttpRequest<any> {
        return request.clone({
            headers: request.headers.append('Access-token', this.tokenStorage.getAccessToken())
        });
    }
}
