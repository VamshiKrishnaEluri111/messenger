import { MessageInfo } from './../model/message-info';
import { Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { AuthServiceService } from '../services/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-send-message',
  templateUrl: './send-message.component.html',
  styleUrls: ['./send-message.component.css']
})
export class SendMessageComponent {

  constructor(public service : AuthServiceService, private router: Router){}

  reciever : string = "";
  message : string = "";
  selectedFile : any;
  @ViewChild('fileRef') fileInput!: ElementRef;


  isRecieverNotSelected : Boolean = false;
  isFileSelected: Boolean = false;
  isFileSizeTooBig : Boolean = false;
  isMesssageNotPresent : Boolean = false;
  private readonly maxSizeInBytes = 2097152; // 2MB
  // private readonly maxSizeInBytes5kb = 5120; // 5KB
  // allUsernames : String[] = [];


  fileSizeTooBigMsg : string = "File size too big, must be below 2MB";
  recieverNotSelectedMsg : string = "please select a recipient";
  isMesssageNotPresentMsg : string = "please write a message or attach a file to send";



  searchTerm: string = '';
  showDropdown = false;

  onSearchTermChange() {
    this.reciever = this.searchTerm;
    console.log("inside serch term change  =  " + this.searchTerm);
    this.service.filteredUsernames = this.service.allUsernames
      .filter(username => username.toLowerCase().includes(this.searchTerm.toLowerCase()));
    this.showDropdown = this.service.filteredUsernames.length > 0;

  }

  selectName(name: string) {
    this.reciever = name;
    this.searchTerm = name;
    this.onSearchTermChange();
    this.showDropdown = false;
  }







  ngOnInit(): void {
    this.isRecieverNotSelected = false;
    this.isFileSelected = false;
    this.isFileSizeTooBig = false;
    this.isMesssageNotPresent = false;
    this.showDropdown = true;

    this.service.getAllRecievers();
  }


  onFileChange(event:any): void {
    this.selectedFile = event.target.files[0];
    this.isFileSelected = true;
  }

  goToHome(){
    this.router.navigate(['home']);
  }

  logout(){
    var confirm = window.confirm("logout of your account");
    if(confirm){
      this.service.logout()
    }
  }






  submit(){
    var formData : FormData = new FormData();
    if(this.reciever !== "" &&
        this.service.allUsernames
        .filter(username => username.toLowerCase() === this.reciever.toLowerCase()).length > 0 )
      {
      this.isRecieverNotSelected = false;
      formData.append('reciever', this.reciever)

      if(this.message==="" && this.selectedFile === undefined){
        this.isMesssageNotPresent = true;
      }
      else{
        this.isMesssageNotPresent = false;

        formData.append('message', this.message);
        if(this.selectedFile !== undefined){
          if(this.selectedFile.size < this.maxSizeInBytes){
            this.isFileSizeTooBig = false;
            formData.append('file', this.selectedFile);
            this.service.sendFile(formData);
          }
          else{
            this.isFileSizeTooBig = true;
          }
        }
        else{
           this.service.sendMessage(formData);
        }
      }
    }else{
      this.isRecieverNotSelected = true;
    }
    this.message = "";
    this.fileInput.nativeElement.value ="";
    this.selectedFile = undefined;
  }
}
