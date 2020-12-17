import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StarterService {

  constructor(private http: HttpClient) { }

  getRegisterForm() : Observable<any>{
    return this.http.get("http://localhost:8081/api/starter/reader-registration") as Observable<any>;
  }
}
