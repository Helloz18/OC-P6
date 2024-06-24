import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { PostForListDTO } from '../../interfaces/post-dto';
import { CommonModule } from '@angular/common';
import { PostComponent } from "../post/post.component";
import { MatIconModule } from '@angular/material/icon';

@Component({
    selector: 'app-post-list',
    standalone: true,
    templateUrl: './post-list.component.html',
    styleUrl: './post-list.component.scss',
    imports: [MatButtonModule, CommonModule, PostComponent, MatIconModule]
})
export class PostListComponent implements OnInit {

  listOfPosts: PostForListDTO[] = [];
  down: boolean = true;

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
        this.sortByDate();
      }
    })
  }

  sortByDate() {
    if(this.down) {
    this.listOfPosts.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
  } else {
    this.listOfPosts.sort((a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
  }
  }
  trier() {
    this.down = !this.down;
    this.sortByDate();
  }
}
