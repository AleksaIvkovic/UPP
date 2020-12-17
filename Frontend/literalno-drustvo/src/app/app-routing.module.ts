import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegistrationComponent } from './components/registration/registration.component';
import { StarterComponent } from './components/starter/starter.component';

const routes: Routes = [
  {path: '', redirectTo:'starter', pathMatch: 'full'},
  {path: 'starter', component: StarterComponent},
  {path: 'register-reader', component: RegistrationComponent},
  {path: 'register-beta', component: RegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
