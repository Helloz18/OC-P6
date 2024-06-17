import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TopicService } from "../../services/topic.service";

@Component({
  selector: 'app-topic',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {

  topics: any;

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.topicService.getAll().subscribe({
      next: (data) => {
        this.topics = data;
      }
    })
  }


}
