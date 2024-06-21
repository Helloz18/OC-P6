import { Component, Input } from '@angular/core';
import { PostForListDTO } from '../../interfaces/post-dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent {

  @Input()
  post!: PostForListDTO;

}
