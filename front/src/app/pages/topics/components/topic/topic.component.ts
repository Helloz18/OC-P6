import { Component, Input } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Topic } from "../../interfaces/topic";
import { MatButtonModule } from "@angular/material/button";

@Component({
  selector: 'app-topic',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent {

  @Input()
  topic!: Topic;


}
