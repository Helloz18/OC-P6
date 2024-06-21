import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { PostForListDTO } from '../../interfaces/post-dto';
import { CommonModule } from '@angular/common';
import { PostComponent } from "../post/post.component";

@Component({
    selector: 'app-post-list',
    standalone: true,
    templateUrl: './post-list.component.html',
    styleUrl: './post-list.component.scss',
    imports: [MatButtonModule, CommonModule, PostComponent]
})
export class PostListComponent implements OnInit {

  listOfPosts: PostForListDTO[] = [];

    constructor(private router: Router, private postService: PostService){}
  
    ngOnInit(): void {
      this.getPostsToRead();
  }

  createPost() {
    this.router.navigateByUrl('posts/create');
  }

  getPostsToRead() {
    this.postService.getListOfPostsToReadByUser().subscribe({
      next: (listOfPosts) => {
        this.listOfPosts = listOfPosts;
      }
    })
  }

}
