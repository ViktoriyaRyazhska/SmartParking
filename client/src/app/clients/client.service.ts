import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Client} from "../model/view/client";
import {ClientRequest} from "./client-request";
import {ProviderRequest} from "../superuser-configuration/providers/add-provider/provider-request";
import {ClientsProviderRequest} from "./clients-provider-request";

@Injectable()
export class ClientService {

    private clientsUrl = 'http://localhost:8080/clients';

    constructor(private http: HttpClient) {
    }

    getClients(): Observable<Client[]> {
        return this.http.get<Client[]>(this.clientsUrl);
    }

    getClientDetail(id: number): Observable<Client> {
        return this.http.get<Client>(this.clientsUrl + "/" + id);
    }

    getClientDetailToEdit(id: number): Observable<ClientRequest> {
        return this.http.get<ClientRequest>(this.clientsUrl + "/" + id);
    }

    updateClient(id: number, client: ClientRequest) {
        return this.http.post(this.clientsUrl + '/update/' + id, client);
    }

    getProviderByClientId(id: number): Observable<ClientsProviderRequest> {
        return this.http.get<ClientsProviderRequest>(this.clientsUrl + "/findprovider/" + id);
    }

}
