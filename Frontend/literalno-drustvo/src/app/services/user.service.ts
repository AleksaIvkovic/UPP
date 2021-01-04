import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    private http: HttpClient
  ) { }

  submitCamundaForm(data, taskId) {
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitCamundaFormWithVariable(data, taskId, variableName) {
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
  }

  getRegisterForm() : Observable<any>{
    return this.http.get("http://localhost:8081/api/register/form/".concat(sessionStorage.getItem("processId"))) as Observable<any>;
  }

  submitRegisterForm(user, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-registration-form/".concat(taskId), user);
  }

  submitVoteForNewWriter(data, taskId) {
    return this.http.post("http://localhost:8081/api/register/submit-vote-new-writer/".concat(taskId), data);
  }

  submitCommentManuscript(data, taskId) {
    return this.http.post("http://localhost:8081/api/book-publishing/submit-commentManuscript-form/".concat(taskId), data);
  }

  confirmEmail(token, processId) {
    let object = {
      "token": token
    };

    return this.http.post("http://localhost:8081/api/register/confirm-email/".concat(processId), object);
  }

  getTasks() {
    return this.http.get("http://localhost:8081/api/tasks/get");
  }

  getTask(taskId) {
    return this.http.get("http://localhost:8081/api/tasks/getSingleTask/".concat(taskId));
  }
}
