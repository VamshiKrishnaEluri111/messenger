import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { InboxComponent } from './inbox/inbox.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { OutboxComponent } from './outbox/outbox.component';
import { SendMessageComponent } from './send-message/send-message.component';
import { SignupPageComponent } from './signup-page/signup-page.component';

const routes: Routes = [
  {path : "", redirectTo: "login", pathMatch : "full"},
  {path : 'login', component : LoginPageComponent},
  {path : 'signup', component : SignupPageComponent},
  {path : 'home', component : HomePageComponent},
  {path : 'sendMessage', component : SendMessageComponent},
  {path : 'inbox', component : InboxComponent},
  {path : 'outbox', component : OutboxComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
