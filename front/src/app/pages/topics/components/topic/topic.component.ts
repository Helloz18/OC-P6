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
  @Input()
  forProfile!: boolean;

  @Output() 
  updateTopicList = new EventEmitter<number>();

  constructor(private userProfileService: UserProfileService) {}

  unsubscribe(topicId: number){
    console.log("email:"+ this.email)
    this.userProfileService.unsubscribe(topicId, this.email).subscribe({
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

  subscribe(topicId: number) {
    // TODO on créé un nouvelle méthode dans le back
    // utilise le header et le token pour récupérer le user --> oui
    // ou alors on stocke au log in l'email dans la session et on le récupère --> non
    // problème pour le moment, si le user est déjà abonné alors il sera désabonné avec ma méthode
    // donc il faut que je fasse deux méthodes dans le back. 
    // --> plus logique pour la séparation des responsabilités

  }
}
