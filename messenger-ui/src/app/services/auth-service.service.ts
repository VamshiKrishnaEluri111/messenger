import { MessageInfo } from './../model/message-info';
import { SendMessageComponent } from './../send-message/send-message.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserInfoModel } from '../model/user-info-model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {


  constructor(private http: HttpClient, private router: Router) { }
  successResponse : string = "";
  isBadRes : Boolean = false;
  isGoodRes : Boolean = false;
  errorResponce : any;
  loginErrorResponce : string = "";
  userAlreadyExists: any;
  userAlreadyExistsMessage: any;



  token : string = "";
  userPersonalName : string = "";


  public resetResponse(){
    this.successResponse = "";
    this.isBadRes = false;
    this.isGoodRes = false;
    this.errorResponce = "";
    this.userAlreadyExists = false;
    this.loginErrorResponce = "";
  }









  public register(uim : UserInfoModel) : void {
    this.resetResponse();

    this.http.post<any>("http://localhost:8080/auth/register", uim
    // , { responseType: 'text' }
    ).subscribe(
      res => {
        this.isBadRes = false;
        this.isGoodRes = true;
        alert("Registered Successfully");
        this.router.navigate(['login']);
      },
      err => {
        if (err.status === 409) {
          this.userAlreadyExists = true;
          this.isGoodRes = false;
          this.userAlreadyExistsMessage = err.error.message;
        }else{
          this.isBadRes = true;
          this.isGoodRes = false;
          this.errorResponce = err.error;
        }
      });
  }



  public login(credentials: any){
    this.resetResponse();
    this.http.post<any>("http://localhost:8080/auth/login", credentials).subscribe(
        res => {
          this.isGoodRes = true;
          this.isBadRes = false;

          this.token = res.jwtToken;
          this.userPersonalName = res.name;
          this.router.navigate(['home']);
        },
        err=> {
          this.isGoodRes = false;
          this.isBadRes = true;
          this.loginErrorResponce = err.error;
        }
    );
  }

  public logout(){
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
    this.http.get("http://localhost:8080/logout", { headers }).subscribe();
    this.token = "";
    alert("you are logged out successfully");
    this.router.navigate(['login']);
  }






  sendMessage(formData: FormData):void {
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
     this.http.post("http://localhost:8080/sendMessage", formData,  { headers } ).subscribe(
      res =>  {
        var confir = confirm("Message sent successfully. Want to send one more");
        if(!confir){
          this.router.navigate(['home']);
        }      },
      err =>  {
        if (err.status === 401) {
          alert("Session timed out");
          this.router.navigate(['login']);
        }else{
        alert("Unable to send message");
        }
      }
     );
  }





  sendFile(formData: FormData):void {
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
     this.http.post("http://localhost:8080/sendFile", formData,  { headers }).subscribe(
      res =>  {
        var confir = confirm("Message sent successfully. Want to send one more");
        if(!confir){
          this.router.navigate(['home']);
        }
      },
      err =>  {
        if (err.status === 401) {
          alert("Session timed out");
          this.router.navigate(['login']);
        }else{
        alert("Unable to send message");
        }
      }
      );
  }





  allUsernames : string[] = [];
  filteredUsernames : string[] = [];

  getAllRecievers():void{
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
     this.http.get<string[]>("http://localhost:8080/getAllRecievers",  { headers })
     .subscribe(
        res => {
          this.allUsernames = res;
          this.filteredUsernames = res;
          console.log(this.allUsernames);
        },
        err =>  {
          if (err.status === 401) {
            alert("Session timed out");
            this.router.navigate(['login']);
          }
        }
     );
  }






  inboxMessages : MessageInfo[] = [];
  countOfNotOpenedMessages: number = 0;

  inbox():void{
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
     this.http.get<MessageInfo[]>("http://localhost:8080/inbox",  { headers }).subscribe(
      res => {
        this.inboxMessages = res;
        this.countOfNotOpenedMessages = this.inboxMessages.filter(MessageInfo => !MessageInfo.isOpened).length;
       },
       err => {
        if (err.status === 401) {
          alert("Session timed out");
          this.router.navigate(['login']);
        }
       }
   );
  }




  outboxMessages : MessageInfo[] = [];
  outbox():void{
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
     this.http.get<MessageInfo[]>("http://localhost:8080/outbox",  { headers }).subscribe(
      res => {
        this.outboxMessages = res;
       },
       err => {
        if (err.status === 401) {
          alert("Session timed out");
          this.router.navigate(['login']);
        }
       }
   );
  }



  deleteRecieverMessage(id:number){
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
    this.http.delete(`http://localhost:8080/deleteRecieverMessage/${id}`, { headers }).subscribe(
      res => alert("deleted"),
      err => alert("some error occured")
    );
  }


  deleteSenderMessage(id:number){
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
    this.http.delete(`http://localhost:8080/deleteSenderMessage/${id}`, { headers }).subscribe(
      res => alert("deleted"),
      err => alert("some error occured")
    );
  }

  setIsOpened(id:number){
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
    this.http.get(`http://localhost:8080/messageOpened/${id}`, { headers }).subscribe();
  }

  deleteMyAccount(){
    const headers = new HttpHeaders({
      'Authorization': this.token
    });
    this.http.delete("http://localhost:8080/deleteMyAccount", { headers }).subscribe(
      res => {
        alert("your account has been permanently deleted");
        this.router.navigate(['login']);
      },
      err => alert("some error occured")
    );
  }

}
