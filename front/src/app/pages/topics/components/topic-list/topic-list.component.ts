import { Component, Input } from '@angular/core';
import { Topic } from '../../interfaces/topic';
import { CommonModule } from '@angular/common';
import { TopicComponent } from '../topic/topic.component';
import { TopicService } from '../../services/topic.service';

@Component({
  selector: 'app-topic-list',
  standalone: true,
  imports: [CommonModule, TopicComponent],
  templateUrl: './topic-list.component.html',
  styleUrl: './topic-list.component.scss'
})
export class TopicListComponent {

  @Input()
  userTopics: Topic[] | undefined = [];

  //topics: Topic[] = [];

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    console.log("in topic list ngOnInit()");
    // this.topicService.getAll().subscribe({
    //   next: (data) => {
    //     this.topics = data;
    //   }
    // })
  }

}
