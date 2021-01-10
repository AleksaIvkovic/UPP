import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmailConfirmationComponent } from './components/email-confirmation/email-confirmation.component';
import { MainComponent } from './components/main/main.component';
import { FormComponent } from './components/form/form.component';
import { TasksComponent } from './components/tasks/tasks.component';
import { BooksComponent } from './components/books/books.component';
import { ImageComponent } from './components/image/image.component';

const routes: Routes = [
  {path: '', redirectTo:'main', pathMatch: 'full'},
  {path: 'main', component: MainComponent, children : [
    {path: '', component: ImageComponent, pathMatch: 'full'},
    {path: 'register-reader', component: FormComponent},
    {path: 'books', component: BooksComponent},
    {path: 'register-beta', component: FormComponent},
    {path: 'register-writer', component: FormComponent},
    {path: 'submit-work/:processId', component: FormComponent},
    {path: 'email-confirmation/:token/:processId', component: EmailConfirmationComponent},
    {path: 'tasks', component: TasksComponent, children: [
      {path: ':taskId/:taskName', component: FormComponent}
    ]},
    {path: 'submit-new-book', component: FormComponent},
    {path: 'file-an-appeal', component: FormComponent},
  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
