import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserInfoModel } from '../model/user-info-model';
import { AuthServiceService } from '../services/auth-service.service';

@Component({
  selector: 'app-signup-page',
  templateUrl: './signup-page.component.html',
  styleUrls: ['./signup-page.component.css']
})
export class SignupPageComponent {

  constructor(public service : AuthServiceService, private router: Router){}

  uim : UserInfoModel = new UserInfoModel("","","","",false);
  cnfpassword : string = "";

  isPassworNotdMatched : Boolean = false;
  errorMessage : string = "Passwords should get matched";

  login() {
    this.router.navigate(['login']);
  }

  public submit(){
    this.isPassworNotdMatched = false;
    this.service.resetResponse();
      if(this.uim.password != this.cnfpassword){
        this.isPassworNotdMatched = true;
      }
      else{
        this.isPassworNotdMatched = false;
        this.service.register(this.uim);
      }
  }
}
