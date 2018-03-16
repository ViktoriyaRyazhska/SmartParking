import {Component, OnInit} from '@angular/core';
import {Address} from '../../model/view/address';
import {Provider} from '../provider';
import {ProviderService} from '../provider.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ProviderRequest} from './provider-request';

@Component({
    selector: 'app-add-provider',
    templateUrl: './add-provider.component.html',
    styleUrls: ['./add-provider.component.css']
})
export class AddProviderComponent implements OnInit {

    provider: ProviderRequest;

    providerForm: FormGroup;

    address: Address;

    constructor(private providerService: ProviderService) {
    }

    ngOnInit() {
        this.providerForm = new FormGroup({
            name: new FormControl('', [
                Validators.required]),
            state: new FormControl('', [
                Validators.required]),
            city: new FormControl('', [
                Validators.required]),
            street: new FormControl('', [
                Validators.required]),
            buildingNumber: new FormControl('', [
                Validators.required])
        })
        ;
    }

    saveProvider(): void {
        this.provider = this.providerForm.value;
        this.providerService.saveProvider(this.provider).subscribe(data => {
            alert('User created successfully.');
        });
        ;
    }
}
