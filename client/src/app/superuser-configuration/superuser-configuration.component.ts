import {Component, OnInit} from '@angular/core';
import {ProviderService} from "../provider.service";
import {Provider} from "../provider";

@Component({
    selector: 'app-superuser-configuration',
    templateUrl: './superuser-configuration.component.html',
    styleUrls: ['./superuser-configuration.component.css']
})
export class SuperuserConfigurationComponent implements OnInit {

    providers: Provider[];

    constructor(private providerService: ProviderService) {
    }

    ngOnInit() {
    }

    getProviders(): void {
        this.providerService.getAll()
            .subscribe(providers => this.providers = providers);
    }

}
