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
    let variableName='betaGenresForm';
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), genres);
  }

  submitWork(data, taskId){
    let variableName='worksToStore';
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
  }

  submitManuscript(data, taskId){
    let variableName='worksToStore';
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
  }

  submitUpdatedManuscript(data, taskId){
    let variableName='worksToStore';
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
  }

  submitBookSynopsis(data, taskId){
    let variableName = "newPublishedBookForm";
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
  }

  submitBookSynopsisReview(data, taskId){
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitPlagiarismReview(data, taskId){
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitManuscriptReview(data, taskId){
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitSendToBeta(data, taskId){
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitMoreChangesNeeded(data, taskId){
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitExplanation(data, taskId){
    let variableName = 'explanation';
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
  }

  submitLectorNotes(data, taskId) {
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitEditorApproval(data, taskId) {
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(taskId), data);
  }

  submitBetaSelection(data, taskId) {
    let variableName='selectedBetaReadersForm';
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
  }

  submitVoteForNewWriter(data, taskId) {
    return this.http.post("http://localhost:8081/api/register/submit-vote-new-writer/".concat(taskId), data);
  }

  submitPaymentDetails(data, taskId) {
    let variableName='creditCard';
    return this.http.post("http://localhost:8081/api/camunda/submit/".concat(variableName).concat('/').concat(taskId), data);
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
