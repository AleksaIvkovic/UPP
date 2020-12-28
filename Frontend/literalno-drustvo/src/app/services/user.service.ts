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

  getRegisterForm() : Observable<any>{
    return this.http.get("http://localhost:8081/api/register/form/".concat(sessionStorage.getItem("processId"))) as Observable<any>;
  }

  submitRegisterForm(user, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-registration-form/".concat(taskId), user);
  }

  submitBetaForm(genres, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-beta-form/".concat(taskId), genres);
  }

  submitWork(data, taskId){
    return this.http.post("http://localhost:8081/api/register/submit-work/".concat(taskId), data);
  }

  submitManuscript(data, taskId){
    return this.http.post("http://localhost:8081/api/book-publishing/submit-manuscript/".concat(taskId), data);
  }

  submitBookSynopsis(data, taskId){
    return this.http.post("http://localhost:8081/api/book-publishing/submit-synopsis-form/".concat(taskId), data);
  }

  submitBookSynopsisReview(data, taskId){
    return this.http.post("http://localhost:8081/api/book-publishing/submit-synopsis-review/".concat(taskId), data);
  }

  submitPlagiarismReview(data, taskId){
    return this.http.post("http://localhost:8081/api/book-publishing/submit-plagiarism-review/".concat(taskId), data);
  }

  submitManuscriptReview(data, taskId){
    return this.http.post("http://localhost:8081/api/book-publishing/submit-manuscript-review/".concat(taskId), data);
  }

  submitExplanation(data, taskId){
    return this.http.post("http://localhost:8081/api/book-publishing/submit-explanation/".concat(taskId), data);
  }

  submitVoteForNewWriter(data, taskId) {
    return this.http.post("http://localhost:8081/api/register/submit-vote-new-writer/".concat(taskId), data);
  }

  submitPaymentDetails(data, taskId) {
    return this.http.post("http://localhost:8081/api/register/submit-payment-details/".concat(taskId), data);
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
