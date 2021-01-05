import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  login(username, password){
    var user = {
      username: username,
      password: password
    }
    return this.http.post("http://localhost:8081/api/auth/login/", user);
  }

  getLoggedInUser() {
    // const options = {
    //   headers: {'Authorization': 'Bearer ' + sessionStorage.getItem('token')}
    // };

    return this.http.get("http://localhost:8081/api/auth/loggedInUser"); //, options
  }
}
