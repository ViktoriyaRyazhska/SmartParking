import {Injectable} from '@angular/core';
import {
    HttpEvent, HttpHandler, HttpInterceptor, HttpRequest
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import {TokenStorage} from '../auth/token/token-storage';
import {environment} from "../../environments/environment";

@Injectable()
export class InterceptorService implements HttpInterceptor {

    private accessTokenHeader = environment.accessTokenHeader;
    private apiUrl = environment.apiUrl;

    constructor(private tokenStorage: TokenStorage) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log('Go into interceptor 2');
        if (request.url.startsWith(this.apiUrl) && this.tokenStorage.getAccessToken()) {
            request = this.addAuthHeaderToRequest(request);
        }
        console.log('Exit from interceptor 2');
        return next.handle(request);
    }

    private addAuthHeaderToRequest(request: HttpRequest<any>): HttpRequest<any> {
        return request.clone({
            headers: request.headers.append(this.accessTokenHeader, this.tokenStorage.getAccessToken())
        });
    }
}
