import { Injectable } from '@angular/core';
import * as jwt_decode from 'jwt-decode';


const TOKEN_KEY = 'access_token';

@Injectable()
export class TokenStorage {

    isExpired: boolean = false;

    constructor() { }

    public signOut() {
        window.localStorage.removeItem(TOKEN_KEY);
        window.localStorage.clear();
    }

    public static saveToken(token: string) {
        window.localStorage.removeItem(TOKEN_KEY);
        window.localStorage.setItem(TOKEN_KEY,  token);
    }

    public static getToken(): string {
        return localStorage.getItem(TOKEN_KEY);
    }

    public static decodeToken(): string {
        let token = TokenStorage.getToken();
        let decodedToken = jwt_decode(token);
        alert(decodedToken.payload);
        return decodedToken;
    }





}