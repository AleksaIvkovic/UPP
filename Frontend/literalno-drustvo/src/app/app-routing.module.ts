import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmailConfirmationComponent } from './components/email-confirmation/email-confirmation.component';
import { MainComponent } from './components/main/main.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { TaskDetailsComponent } from './components/tasks/task-details/task-details.component';
import { StarterComponent } from './components/starter/starter.component';
import { TasksComponent } from './components/tasks/tasks.component';
import { BooksComponent } from './components/books/books.component';

const routes: Routes = [
  {path: '', redirectTo:'main', pathMatch: 'full'},
  {path: 'main', component: MainComponent, children : [
    {path: 'register-reader', component: RegistrationComponent},
    {path: 'books', component: BooksComponent},
    {path: 'register-beta', component: RegistrationComponent},
    {path: 'register-writer', component: RegistrationComponent},
    {path: 'submit-work/:processId', component: RegistrationComponent},
    {path: 'email-confirmation/:token/:processId', component: EmailConfirmationComponent},
    {path: 'tasks', component: TasksComponent, children: [
      {path: ':taskId/:taskName', component: RegistrationComponent}
    ]},
    {path: 'submit-new-book', component: RegistrationComponent},
    {path: 'file-an-appeal', component: RegistrationComponent},
  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
