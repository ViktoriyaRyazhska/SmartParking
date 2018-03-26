import { Injectable } from '@angular/core';
import {
    HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
    HttpResponse
} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/do';
import {TokenStorage} from "./auth/token/token-storage";
import {Router} from "@angular/router";

@Injectable()
export class InterceptorService implements HttpInterceptor{

  constructor(private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        request = request.clone({
            headers: request.headers.append('Authorization', `Bearer ${TokenStorage.getToken()}`)
        });
        return next.handle(request).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
            }
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401) {
                    this.router.navigate(['/login'])
                }
            }
        });
    }

}
