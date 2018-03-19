import {Component, OnInit} from '@angular/core';
import {Provider} from '../provider';
import {ProviderService} from '../provider.service';
import {ProviderListFilterParameters} from '../../../model/filter/provider-list-filter-parameters';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-provider-list',
    templateUrl: './provider-list.component.html',
    styleUrls: ['./provider-list.component.css']
})
export class ProviderListComponent implements OnInit {
    providers: Provider[];
    providerFilterForm = new FormGroup({
        active: new FormControl('null', []),
        companyName: new FormControl('', [])
    });
    providerFilter: ProviderListFilterParameters;

    constructor(private providerService: ProviderService,
                private formBuilder: FormBuilder) {
    }

    ngOnInit() {
        this.getProviders();
    }

    getProviders(): void {
        this.providerFilter = this.providerFilterForm.value;
        console.log(this.providerFilter);
        this.providerService.getProviders(this.providerFilter)
            .subscribe(providers => this.providers = providers);
    }

}
