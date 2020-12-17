import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmailConfirmationComponent } from './components/email-confirmation/email-confirmation.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { StarterComponent } from './components/starter/starter.component';

const routes: Routes = [
  {path: '', redirectTo:'starter', pathMatch: 'full'},
  {path: 'starter', component: StarterComponent},
  {path: 'register-reader', component: RegistrationComponent},
  {path: 'register-beta', component: RegistrationComponent},
  {path: 'email-confirmation/:username/:token/:processId', component: EmailConfirmationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
