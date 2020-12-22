import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmailConfirmationComponent } from './components/email-confirmation/email-confirmation.component';
import { MainComponent } from './components/main/main.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { StarterComponent } from './components/starter/starter.component';
import { TaskDetailsComponent } from './components/tasks/task-details/task-details.component';
import { TasksComponent } from './components/tasks/tasks.component';

const routes: Routes = [
  {path: '', redirectTo:'main', pathMatch: 'full'},
  {path: 'main', component: MainComponent, children : [
    {path: 'register-reader', component: RegistrationComponent},
    {path: 'register-beta', component: RegistrationComponent},
    {path: 'register-writer', component: RegistrationComponent},
    {path: 'submit-work/:processId', component: RegistrationComponent},
    {path: 'email-confirmation/:token/:processId', component: EmailConfirmationComponent},
    {path: 'tasks', component: TasksComponent, children: [
      {path: ':taskId', component: TaskDetailsComponent}
    ]},


  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
