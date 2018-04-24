import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from "@angular/router";
import {TokenStorage} from "../token/token-storage";

@Injectable()
export class DriverGuard {

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
        if(this.tokenStorage.getRole() == 'DRIVER' || this.tokenStorage.getRole() == 'PROVIDER_MANAGER' || this.tokenStorage.getRole() == 'SUPERUSER') {
            return true;
        } else {
            this.router.navigate(['/error/forbidden']);
            return false;
        }
    }

}
