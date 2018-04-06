import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Token} from "./token";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../environments/environment";
import {getToken} from "codelyzer/angular/styles/cssLexer";

const TOKEN_KEY = 'access_token';
const helper = new JwtHelperService();


@Injectable()
export class TokenStorage {

    private token = window.localStorage.getItem(TOKEN_KEY);

    constructor() {
    }

    public signOut() {
        window.localStorage.removeItem(TOKEN_KEY);
        window.localStorage.clear();
        this.token = null;
    }

    public saveToken(token: string) {
        console.log('Save token ' + token);
        window.localStorage.removeItem(TOKEN_KEY);
        window.localStorage.setItem(TOKEN_KEY, token);
        this.token = window.localStorage.getItem(TOKEN_KEY);
        console.log('Get saved token ' + this.token);
    }

    public getToken(): string {
        return this.token;
    }

    public isExpired(): boolean {
        if(this.token == null) {
            return false;
        }
        return helper.isTokenExpired(this.token);
    }

    public getRole(): string {
        if(!this.hasToken()) {
            return '';
        }
        return this.decodeToken().authorities[0].authority;
    }

    public getUsername(): string {
        return this.decodeToken().username;
    }

    private decodeToken(): any {
        return helper.decodeToken(this.token);
    }

    public hasToken(): boolean {
        return this.token != null;
    }
}