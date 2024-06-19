import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicComponent } from '../topic/topic.component';
import { TopicService } from '../../services/topic.service';
import { UserProfile } from 'src/app/pages/userProfile/interfaces/user-profile';

@Component({
  selector: 'app-topic-list',
  standalone: true,
  imports: [CommonModule, TopicComponent],
  templateUrl: './topic-list.component.html',
  styleUrl: './topic-list.component.scss'
})
export class TopicListComponent {

  @Input()
  user!: UserProfile;

  @Output()
  topicListUpdated = new EventEmitter<number>();

  //topics: Topic[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
  }

  topicUpdated(topicId: number) {
    this.topicListUpdated.emit(topicId);
  }

}
