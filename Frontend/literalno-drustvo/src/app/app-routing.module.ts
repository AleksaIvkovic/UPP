import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmailConfirmationComponent } from './components/email-confirmation/email-confirmation.component';
import { MainComponent } from './components/main/main.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { StarterComponent } from './components/starter/starter.component';

const routes: Routes = [
  {path: '', redirectTo:'main', pathMatch: 'full'},
  {path: 'main', component: MainComponent, children : [
    {path: 'register-reader', component: RegistrationComponent},
    {path: 'register-beta', component: RegistrationComponent},
    {path: 'register-writer', component: RegistrationComponent},
    {path: 'email-confirmation/:token/:processId', component: EmailConfirmationComponent}
  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
