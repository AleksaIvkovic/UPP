import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getRegisterForm() : Observable<any>{
    return this.http.get("http://localhost:8081/api/register/registration-form/".concat(sessionStorage.getItem("processId"))) as Observable<any>;
  }

  submitRegisterForm(user, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-form/".concat(taskId), user);
  }

  submitBetaForm(genres, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-beta-form/".concat(taskId), genres);
  }

  submitWork(data, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-work/".concat(taskId), data);
  }

  confirmEmail(token, processId) {
    let object = {
      "token": token
    };

    return this.http.post("http://localhost:8081/api/register/confirm-email/".concat(processId), object);
  }

  login(username, password){
    var user = {
      Username: username,
      Password: password
    }
    return this.http.post("http://localhost:8081/api/register/login/", user);
  }
}
