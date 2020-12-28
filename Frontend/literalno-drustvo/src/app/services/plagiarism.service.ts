import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PlagiarismService {
  constructor(
    private http: HttpClient
  ) { }

  submitAppealForm(form, taskId){
    return this.http.post("http://localhost:8081/api/plagiarism/submit-appeal/".concat(taskId), form);
  }

  submitChosenEditorsForm(form, taskId){
    return this.http.post("http://localhost:8081/api/plagiarism/submit-chosen-editors/".concat(taskId), form);
  }

  submitEditorReviewForm(form, taskId){
    return this.http.post("http://localhost:8081/api/plagiarism/submit-editor-review/".concat(taskId), form);
  }

  submitCommitteeReviewForm(form, taskId){
    return this.http.post("http://localhost:8081/api/plagiarism/submit-committee-review/".concat(taskId), form);
  }
}
