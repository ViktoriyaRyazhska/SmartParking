import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {TokenStorage} from "./auth/token/token-storage";

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private tokenStorage: TokenStorage,
                private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        return this.checkRights();
    }

    checkRights(): boolean {
        this.router.navigate(['/index']);
        return this.tokenStorage.hasToken()
    }
}



