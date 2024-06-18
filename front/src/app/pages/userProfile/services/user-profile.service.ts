import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { UserProfile } from '../interfaces/user-profile';

const BACKEND_URL= environment.backendUrl + "/profile"

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Authorization',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  constructor(private http: HttpClient) { }

  getUserProfile() {
    return this.http.get<UserProfile>(BACKEND_URL);
  }
}
