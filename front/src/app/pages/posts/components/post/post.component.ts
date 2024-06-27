import { Component, Input } from '@angular/core';
import { PostDTO } from '../../interfaces/post-dto';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent {

  @Input()
  post!: PostDTO;

  constructor(private router: Router) {}

  goToPost() {
    this.router.navigateByUrl('/posts/'+this.post.id);
  }
}
