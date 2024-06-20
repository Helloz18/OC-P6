import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { PostDTO } from '../interfaces/post-dto';

const BACKEND_URL= environment.backendUrl + "/post"

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  savePost(postDTO: PostDTO) {
    return this.http.post( BACKEND_URL, postDTO );
  }
}
