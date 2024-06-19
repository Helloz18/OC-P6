import { Component, EventEmitter, Input, Output } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Topic } from "../../interfaces/topic";
import { MatButtonModule } from "@angular/material/button";
import { UserProfileService } from "src/app/pages/userProfile/services/user-profile.service";

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
  @Input()
  email!: string;

  @Output() 
  updateTopicList = new EventEmitter<number>();

  constructor(private userProfileService: UserProfileService) {}

  unsubscribe(topicId: number){
    console.log("email:"+ this.email)
    this.userProfileService.modifySubscription(topicId, this.email).subscribe({
      next: (data) => {
          let message = JSON.parse(JSON.stringify(data)).message;
          alert(message);
          this.updateTopicList.emit(topicId);
      },
      error: (error) => {
        alert(error.error.message);
      }
    })
  }
}
