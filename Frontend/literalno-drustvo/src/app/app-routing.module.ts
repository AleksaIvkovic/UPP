import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegistrationComponent } from './components/registration/registration.component';

const routes: Routes = [
  {path: '', redirectTo:'register-reader', pathMatch: 'full'},
  {path: 'register-reader', component: RegistrationComponent},
  {path: 'register-beta', component: RegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
