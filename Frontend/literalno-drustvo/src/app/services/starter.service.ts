import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StarterService {
 

  constructor(private http: HttpClient) { }

  startReaderRegistration() : Observable<any>{
    return this.http.get("http://localhost:8081/api/starter/start/registerReader") as Observable<any>;
  }

  startWriterRegistration() : Observable<any>{
    return this.http.get("http://localhost:8081/api/starter/start/registerWriter") as Observable<any>;
  }

  startBookPublishing() : Observable<any>{
    return this.http.get("http://localhost:8081/api/starter/start-writer-process/bookPublishing") as Observable<any>;
  }

  startWriterProcess(processName: string) {
    return this.http.get("http://localhost:8081/api/starter/start-writer-process/".concat(processName)) as Observable<any>;
  }
}
