import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {TokenStorage} from "./auth/token/token-storage";

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

    constructor(private tokenStorage: TokenStorage,
                private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        return this.checkRights();
    }

    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        return this.canActivate(childRoute, state);
    }

    checkRights(): boolean {
        if(this.tokenStorage.hasToken()) {
            this.router.navigate(['/index']);
            return false;
        } else {
            return true;
        }
    }
}



