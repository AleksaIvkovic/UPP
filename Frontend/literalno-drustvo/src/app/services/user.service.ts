import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getRegisterForm() : Observable<any>{
    return this.http.get("http://localhost:8081/api/register/reader-form/".concat(sessionStorage.getItem("processId"))) as Observable<any>;
  }

  submitRegisterForm(user, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-form/".concat(taskId), user);
  }

  submitBetaForm(genres, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-beta-form/".concat(taskId), genres);
  }
}
