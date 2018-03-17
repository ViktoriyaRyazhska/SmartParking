import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginData} from "./login-data";
import {Router} from "@angular/router";
import {CookieService} from "angular2-cookie/core";
import {ResponseToken} from "./response-token";
import {LoginService} from "./login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    hide: boolean = true;
    loginForm: FormGroup;
    loginData: LoginData;
    error: boolean;

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
                private loginService: LoginService,
                private router: Router,
                private cookie: CookieService) {}

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            email: this.emailControl,
            password: this.passwordControl
        });
    }

    login = () => {
        this.loginData = this.loginForm.value;
        this.loginService.signIn(this.account)
            .subscribe((response:ResponseToken)=>{
                    this.cookie.put('auth',response.token);
                    this.error=false;
                    this.router.navigate(['/']);
                }, error2 => {
                    this.error = true;
                }
            );
    };

}
