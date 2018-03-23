import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

const TOKEN_KEY = 'access_token';
const helper = new JwtHelperService();

@Injectable()
export class TokenStorage {

    constructor() {
    }

    public static signOut() {
        window.localStorage.removeItem(TOKEN_KEY);
        window.localStorage.clear();
    }

    public static saveToken(token: string) {
        window.localStorage.removeItem(TOKEN_KEY);
        window.localStorage.setItem(TOKEN_KEY, token);
    }

    public static getToken(): string {
        return window.localStorage.getItem(TOKEN_KEY);
    }

    public static getRole(): string {
        return TokenStorage.decodeToken().authorities.authority;
    }

    public static getUsername(): string {
        return TokenStorage.decodeToken().username;
    }

    public static decodeToken(): any {
        return helper.decodeToken(TokenStorage.getToken());
    }

    public static hasToken(): boolean {
        if (this.getToken() != null) {
            return true;
        } else {
            return false;
        }
    }
}