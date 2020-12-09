import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getRegisterForm() : Observable<any>{
    return this.http.get("http://localhost:8081/api/register/user-form") as Observable<any>;
  }

  submitRegisterForm() : Observable<any>{
    return this.http.put("http://localhost:8081/api/register",null) as Observable<any>;
  }
}
