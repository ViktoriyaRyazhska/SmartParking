import {Injectable} from '@angular/core';
import {
    HttpClient,
    HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
    HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import {TokenStorage} from './auth/token/token-storage';
import {Token} from "./auth/token/token";
import {environment} from "../environments/environment";

@Injectable()
export class InterceptorService implements HttpInterceptor {

    constructor() {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token = TokenStorage.getToken();
        if (request.url.startsWith('http://localhost:8080/') && (token)) {
            request = request.clone({
                headers: request.headers.append('Authorization', `Bearer ${token}`)
            });
        }
        return next.handle(request).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
            }
        }, (error: any) => {
            if (error instanceof HttpErrorResponse) {
                if(error.status === 401) {
                    TokenStorage.saveToken(error.error)
                }
            }
        });
    }
}
