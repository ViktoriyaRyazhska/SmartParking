import {Component, OnInit} from '@angular/core';
import {Provider} from '../../../model/view/provider';
import {ProviderService} from '../provider.service';
import {ProviderListFilterParameters} from '../../../model/filter/provider-list-filter-parameters';
import {FormControl, FormGroup,} from '@angular/forms';
import {ProviderStatisticRequest} from './ProviderStatisticRequest';

@Component({
    selector: 'app-provider-list',
    templateUrl: './provider-list.component.html',
    styleUrls: ['./provider-list.component.css']
})
export class ProviderListComponent implements OnInit {
    providers: Provider[];
    providerFilterForm = new FormGroup({
        active: new FormControl('all', []),
        companyName: new FormControl('', [])
    });

    providerStatistic: ProviderStatisticRequest;

    providerFilter: ProviderListFilterParameters;

    constructor(private providerService: ProviderService) {
    }

    ngOnInit() {
        this.getProviders();
        this.getAmount();
    }

    getProviders(): void {
        this.providerFilter = this.providerFilterForm.value;
        this.providerService.getAll(this.providerFilter)
            .subscribe(providers => this.providers = providers);
    }

    getAmount(): void {
        this.providerService.getAmount()
            .subscribe(providerStatistic => this.providerStatistic = providerStatistic);
    }
}
