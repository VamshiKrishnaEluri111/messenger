import { AuthServiceService } from './../services/auth-service.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  constructor(private router: Router, public service : AuthServiceService){ }

  ngOnInit(): void {
    this.service.resetResponse();
  }


  credentials: { username: string;password: string } = {
    username: '',
    password: ''
  };

  login() {
    this.service.resetResponse();
    this.service.login(this.credentials);
  }

  Signup() {
    this.router.navigate(['signup']);
  }
}
