import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {Provider} from '../../model/view/provider';
import {Observable} from 'rxjs/Observable';
import {ProviderRequest} from './add-provider/provider-request';
import {ProviderListFilterParameters} from '../../model/filter/provider-list-filter-parameters';

@Injectable()
export class ProviderService {

    private providerUrl = 'http://localhost:8080/providers';

    constructor(private http: HttpClient) {
    }

    getProviders(providerFilter: ProviderListFilterParameters): Observable<Provider[]> {
        var params = {
            active: providerFilter.active,
            companyName: providerFilter.companyName
        };
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
