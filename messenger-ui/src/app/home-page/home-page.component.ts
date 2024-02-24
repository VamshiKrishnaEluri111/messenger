import { AuthServiceService } from './../services/auth-service.service';
import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent {

  constructor(private router: Router, public service : AuthServiceService){
  }

  sendMessage() {
    this.router.navigate(['sendMessage']);
  }

  inbox() {
    // this.service.inbox();
    this.router.navigate(['inbox']);
  }

  outbox() {
    this.router.navigate(['outbox']);
  }

  logout(){
    var confirm = window.confirm("logout of your account");
    if(confirm){
      this.service.logout()
    }
  }


  deleteAccount(){
    var confirm = window.confirm(`are you sure, Do you want to delete your account?   If you click "OK" your account will be permanently deleted and will not be recovered`);
    if(confirm){
      this.service.deleteMyAccount()
    }
  }
}
