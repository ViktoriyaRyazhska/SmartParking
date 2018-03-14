import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Client} from "./clients";

@Injectable()
export class ClientService {

    private clientsUrl = 'http://localhost:8080/clients';

    constructor(private http: HttpClient) {
    }

    getClients(): Observable<Client[]> {
        return this.http.get<Client[]>(this.clientsUrl);
    }

}