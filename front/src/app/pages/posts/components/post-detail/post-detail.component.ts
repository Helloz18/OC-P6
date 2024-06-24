import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { PostDTO } from '../../interfaces/post-dto';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatInputModule, MatFormFieldModule],
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.scss'
})
export class PostDetailComponent implements OnInit {

  id: number = 0;
  postDTO!: PostDTO;

  constructor(private route: ActivatedRoute, private router: Router, private postService: PostService) {
    this.id = this.route.snapshot.params['index'];
  }

  ngOnInit(): void {
    this.getPost();
  }

  getPost() {
    this.postService.getPostById(this.id).subscribe({
      next: (postDTO) => {
        this.postDTO = postDTO;
      },
      error: (error) => {
        alert(error.error.message);
      }
    })
  }

  sendComment(comment:string) {
    console.log(comment);
    this.postService.saveComment(this.id, comment).subscribe({
      next: () => {
        alert("Commentaire enregistré");
      },
      error: (error) => {
        alert(error.error.message);
      }
    })
  }

  previous() {
    this.router.navigateByUrl('/posts');
  }
}
