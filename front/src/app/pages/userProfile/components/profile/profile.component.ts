import { Component, OnInit } from '@angular/core';
import { UserProfile } from '../../interfaces/user-profile';
import { UserProfileService } from '../../services/user-profile.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { TopicListComponent } from 'src/app/pages/topics/components/topic-list/topic-list.component';
import { Topic } from 'src/app/pages/topics/interfaces/topic';
import { TokenStorageService } from 'src/app/pages/auth/services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule, TopicListComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  userProfile!: UserProfile;
  userTopics: Topic[] = [];
  userProfileForm!: FormGroup;

  constructor(private fb: FormBuilder, private userProfileService: UserProfileService, private tokenStrorageService: TokenStorageService, private router: Router) {
    this.userProfileForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.getUserProfile();
  }

  getUserProfile() {
    this.userProfileService.getUserProfile().subscribe({
      next: (data) => {
        this.userProfile = data;
        this.userTopics = data.topics!;
        console.log(data);
      },
      error: (error) => {
        alert(error.error.message);
      },
      complete: () => {
        this.userProfileForm = this.fb.group({
          name: [this.userProfile.name, [Validators.required]],
          email: [this.userProfile.email, [Validators.required, Validators.email]],
          password: ['', [Validators.required]]
        });
      }
    })
  }
  
  onSubmit() {
    console.log(this.userProfileForm)
  }

  logout() {
    this.tokenStrorageService.signOut();
    this.router.navigateByUrl('/');
  }
}
