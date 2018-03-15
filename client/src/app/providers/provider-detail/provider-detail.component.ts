import {Component, OnInit} from '@angular/core';
import {ProviderService} from "../provider.service";
import {Provider} from "../provider";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-provider-detail',
    templateUrl: './provider-detail.component.html',
    styleUrls: ['./provider-detail.component.css']
})
export class ProviderDetailComponent implements OnInit {

    provider: Provider;

    constructor(private route: ActivatedRoute,
                private providerService: ProviderService) {
    }

    ngOnInit() {
        this.getProviderDetail2();
    }

    getProviderDetail(): void {
        const id = +this.route.snapshot.paramMap.get('id');
        this.providerService.getAll()
            .subscribe(providers => this.provider = providers.find(provider => provider.id === id));
    }

    getProviderDetail2(): void {
        const id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.providerService.getProviderDetail(id)
            .subscribe(provider => this.provider = provider);
    }
}
