import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { UserProfile } from '../interfaces/user-profile';
import { LoginRequest } from '../../auth/interfaces/login-request';

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

  updateUserProfile(email: string, modifiedUser:LoginRequest) {
    return this.http.put(BACKEND_URL +"/"+ email, modifiedUser);
  }

  unsubscribe(topicId: number, email: string) {
    return this.http.put(BACKEND_URL+"/topic/"+topicId+"?email="+email, {});
  }
}
