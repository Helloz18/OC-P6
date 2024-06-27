import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { PostCreateDTO, PostDTO, PostDetailDTO } from '../interfaces/post-dto';

const BACKEND_URL= environment.backendUrl + "/post";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  savePost(postCreateDTO: PostCreateDTO) {
    return this.http.post( BACKEND_URL, postCreateDTO );
  }

  getListOfPostsToReadByUser() {
    return this.http.get<PostDTO[]>( BACKEND_URL );
  }

  getPostById(id: number) {
    return this.http.get<PostDetailDTO>( BACKEND_URL + '/'+id);
  }

  saveComment(postId: number, content: string) {
    return this.http.post(BACKEND_URL + '/' + postId + '/comment', content);
  }
}
