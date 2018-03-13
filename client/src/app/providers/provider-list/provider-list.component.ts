import { Component, OnInit } from '@angular/core';
import {Provider} from "../provider";
import {ProviderService} from "../provider.service";

@Component({
  selector: 'app-provider-list',
  templateUrl: './provider-list.component.html',
  styleUrls: ['./provider-list.component.css']
})
export class ProviderListComponent implements OnInit {
    providers: Provider[];

    constructor(private providerService: ProviderService) { }

    ngOnInit() {
        this.getProviders();
    }

    getProviders(): void {
        this.providerService.getAll()
            .subscribe(providers => this.providers = providers);
    }

}
