import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { LoginRequest } from '../interfaces/login-request';
import { Observable } from 'rxjs';


const BACKEND_URL= environment.backendUrl + "/auth"

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Authorization',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  
  /**
   * login method
   * @returns
   */
  login(loginRequest: LoginRequest): Observable<any> {
    const req = new HttpRequest(
      'POST',
      BACKEND_URL + '/login',
      loginRequest,
      httpOptions
    );
    return this.http.request(req);
  }

  /**
   * register method
   * @returns
   */
  register(loginRequest: LoginRequest): Observable<any> {
    const req = new HttpRequest(
      'POST',
      BACKEND_URL + '/register',
      loginRequest,
      httpOptions
    );
    return this.http.request(req);
  }
}
