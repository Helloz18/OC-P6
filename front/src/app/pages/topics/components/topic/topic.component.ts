import { Component, Input } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Topic } from "../../interfaces/topic";

@Component({
  selector: 'app-topic',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent {

  @Input()
  topic!: Topic;


}
