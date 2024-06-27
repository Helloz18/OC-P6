import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Topic } from '../../interfaces/topic';
import { MatButtonModule } from '@angular/material/button';
import { UserProfileService } from 'src/app/pages/userProfile/services/user-profile.service';
import { TopicService } from '../../services/topic.service';

@Component({
  selector: 'app-topic',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss'],
})
export class TopicComponent {
  @Input()
  topic!: Topic;
  @Input()
  email!: string;
  @Input()
  forProfile!: boolean;

  @Output()
  updateTopicList = new EventEmitter<number>();

  constructor(
    private userProfileService: UserProfileService,
    private topicService: TopicService
  ) {}

  unsubscribe(topicId: number) {
    console.log('email:' + this.email);
    this.userProfileService.unsubscribe(topicId, this.email).subscribe({
      next: (data) => {
        let message = JSON.parse(JSON.stringify(data)).message;
        alert(message);
        this.updateTopicList.emit(topicId);
      },
      error: (error) => {
        alert(error.error.message);
      }
    });
  }

  subscribe(topicId: number) {
    this.topicService.subscribe(topicId).subscribe({
      next: (data) => {
        let message = JSON.parse(JSON.stringify(data)).message;
        alert(message);
      },
      error: (error) => {
        alert(error.error.message);
      }
    });
  }
}
