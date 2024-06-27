import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicComponent } from '../topic/topic.component';
import { TopicService } from '../../services/topic.service';
import { UserProfile } from 'src/app/pages/userProfile/interfaces/user-profile';
import { Topic } from '../../interfaces/topic';

@Component({
  selector: 'app-topic-list',
  standalone: true,
  imports: [CommonModule, TopicComponent],
  templateUrl: './topic-list.component.html',
  styleUrl: './topic-list.component.scss',
})
export class TopicListComponent implements OnInit {
  @Input()
  user!: UserProfile;
  @Input()
  comesFromProfile!: boolean;

  @Output()
  topicListUpdated = new EventEmitter<number>();

  allTopics: Topic[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    if (!this.comesFromProfile) {
      this.getAllTopics();
    }
  }

  topicUpdated(topicId: number) {
    this.topicListUpdated.emit(topicId);
  }

  getAllTopics() {
    this.topicService.getAll().subscribe({
      next: (topics) => {
        this.allTopics = topics;
      },
      error: (error) => {
        alert(error.error.message);
      }
    });
  }
}
