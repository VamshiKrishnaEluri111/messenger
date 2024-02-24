import { AuthServiceService } from './../services/auth-service.service';
import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-outbox',
  templateUrl: './outbox.component.html',
  styleUrls: ['./outbox.component.css']
})
export class OutboxComponent {
  constructor(public service : AuthServiceService, private http: HttpClient, private router: Router){}

  activeMessage : any;

  ngOnInit(): void {
    // this.service.getAllRecievers();
      this.service.outbox();
  }
  goToHome(){
    this.router.navigate(['home']);
  }
  goToReply(){
    this.router.navigate(['sendMessage']);
  }

  logout(){
    var confirm = window.confirm("logout of your account");
    if(confirm){
      this.service.logout()
    }
  }



  indexOfMessageInfo : number = -1;

  getOutboxMessage(id:number, index : number):void{
    this.indexOfMessageInfo = index;
    this.activeMessage = this.service.outboxMessages.find((user => user.id === id));
  }

  deleteSenderMessage(id:number){
    var confirmed = window.confirm("delete the message")
    if(confirmed){
      this.service.outboxMessages.splice(this.indexOfMessageInfo,1);
      this.activeMessage = undefined;
      this.indexOfMessageInfo = -1;
      this.service.deleteSenderMessage(id);
    }
  }

  download() {
    const headers = new HttpHeaders({
      'Authorization': this.service.token
    });

    const fileName = this.activeMessage.fileName;
    const folderPath = this.activeMessage.fileData;
    var formData : FormData = new FormData();

    formData.append("folderPath" , folderPath );
    formData.append("fileName" , fileName );
      this.http.post("http://localhost:8080/download", formData, { responseType: 'arraybuffer' , headers})
      .subscribe((data: ArrayBuffer) => {

        console.log(folderPath)
        // const fileName = this.getFileNameFromUrl(this.activeMessage.fileData);
        const fileName = this.activeMessage.fileName;
        console.log(fileName)
        // const fullPath = "D:/learning/Messenger/messenger/src/assets/";
        const basePath = "/src/assets/local-storage/"; // The base path you want to extract
        const relativePath = folderPath.substring(folderPath.indexOf(basePath));
        console.log(relativePath);



        const blob = new Blob([data]);
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = fileName;
        link.click();
      },
      err => {
        if (err.status === 401) {
          // alert("Session timed out");
          this.router.navigate(['login']);
        }
       }
      );
  }






  // downloadFile(): void {
  //   const filePath = this.activeMessage.fileData;
  //   console.log(filePath)
  //   // const fileName = this.getFileNameFromUrl(this.activeMessage.fileData);
  //   const fileName = this.activeMessage.fileName;
  //   console.log(fileName)

  //   // const fullPath = "D:/learning/Messenger/messenger/src/assets/";

  //   const basePath = "/src/assets/local-storage/"; // The base path you want to extract
  //   const relativePath = filePath.substring(filePath.indexOf(basePath));
  //   console.log(relativePath);

  //   const link = document.createElement('a');
  //   // link.href = "src\assets\login.jpg";
  //   // link.download = "login.jpg" || "file";
  //   link.href = relativePath;
  //   link.download = fileName || "file";
  //   document.body.appendChild(link);
  //   link.click();
  //   document.body.removeChild(link);
  // }

  // getFileNameFromUrl(url: string): string | undefined {
  //   try {
  //     const urlObj = new URL(url);
  //     return urlObj.pathname.split('/').pop() || undefined;
  //   } catch (error) {
  //     console.error('Error parsing URL:', error);
  //     return undefined;
  //   }
  // }
}
