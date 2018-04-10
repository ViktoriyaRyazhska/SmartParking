import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginData} from "./login-data";
import {Router} from "@angular/router";
import {TokenPair} from "../token/token-pair";
import {TokenStorage} from "../token/token-storage";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    hide: boolean = true;
    loginForm: FormGroup;
    loginData: LoginData;

    emailControl: FormControl = new FormControl('', [
        Validators.required,
        Validators.email,
    ]);
    passwordControl: FormControl = new FormControl('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(16),
    ]);

    constructor(private formBuilder: FormBuilder,
                private authService: AuthService,
                private router: Router,
                private tokenStorage: TokenStorage
    ) {}

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            email: this.emailControl,
            password: this.passwordControl
        });
    }


    login = () => {
        this.loginData = this.loginForm.value;
        this.authService.signIn(this.loginData)
            .subscribe((token: TokenPair)=>{
                this.tokenStorage.saveToken(token);
                alert('You are successfully authorized');
                this.router.navigate(['/']);
                }, (error) => {
                if(error instanceof HttpErrorResponse)
                alert(error.error.response);
                }
            );
    };
}


