import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';

import {Provider} from './provider';
import {Observable} from 'rxjs/Observable';
import {ProviderRequest} from './add-provider/provider-request';
import {ProviderListFilterParameters} from '../../model/filter/provider-list-filter-parameters';

@Injectable()
export class ProviderService {

    private providerUrl = 'http://localhost:8080/providers';

    constructor(private http: HttpClient) {
    }

    getProviders(providerFilter: ProviderListFilterParameters): Observable<Provider[]> {
        console.log(providerFilter)
        let params = new HttpParams().set("active", providerFilter.active)
            .set("companyName", providerFilter.companyName);
        return this.http.get<Provider[]>(this.providerUrl, {params: params});
    }

    getProviderDetail(id: number): Observable<Provider> {
        return this.http.get<Provider>(this.providerUrl + '/' + id);
    }

    saveProvider(providerRequest: ProviderRequest) {
        return this.http.post(this.providerUrl + '/add', providerRequest);
    }

    changeState(id: number): Observable<Provider> {
        return this.http.get<Provider>(this.providerUrl + '/changeState/' + id);
    }

}
