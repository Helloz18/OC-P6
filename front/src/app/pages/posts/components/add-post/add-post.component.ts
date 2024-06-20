import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Topic } from 'src/app/pages/topics/interfaces/topic';
import { TopicService } from 'src/app/pages/topics/services/topic.service';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-post',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatIconModule],
  templateUrl: './add-post.component.html',
  styleUrl: './add-post.component.scss'
})
export class AddPostComponent implements OnInit {

  postForm!: FormGroup;

  topics: Topic[] = [];

  constructor(private fb: FormBuilder, private topicService: TopicService, private router: Router) {
    this.postForm = this.fb.group({
      topic: [''],
      title: [''],
      content: ['']
    })
  }
  ngOnInit(): void {
    this.getAllTopics();
  }

  getAllTopics() {
    this.topicService.getAll().subscribe({
      next: (topics) => {
        this.topics = topics;
      },
      error: (error) => {
        alert(error.error.message);
      }
    })
  }

  changeTopic(event: Event) {
    console.log(event);
  }

  onSubmit() {
    console.log(this.postForm.value);
    
  }

  previous() {
    this.router.navigateByUrl('/posts');
  }
}
