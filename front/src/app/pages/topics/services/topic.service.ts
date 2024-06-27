import { HttpClient, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { Topic } from '../interfaces/topic';

const BACKEND_URL= environment.backendUrl + "/topic";

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Topic[]>(BACKEND_URL);
  }

  subscribe(topicId: number) {
    return this.http.put(BACKEND_URL+"/"+topicId, {});
  }
}
