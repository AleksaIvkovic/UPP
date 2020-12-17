import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StarterService {

  constructor(private http: HttpClient) { }

  startReaderRegistration() : Observable<any>{
    return this.http.get("http://localhost:8081/api/starter/registration/registerReader") as Observable<any>;
  }

  startWriterRegistration() : Observable<any>{
    return this.http.get("http://localhost:8081/api/starter/registration/registerWriter") as Observable<any>;
  }
}
