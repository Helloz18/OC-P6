import { Component, OnInit } from '@angular/core';
import { UserProfile } from '../../interfaces/user-profile';
import { UserProfileService } from '../../services/user-profile.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  userProfile!: UserProfile;
  userProfileForm!: FormGroup;

  constructor(private fb: FormBuilder, private userProfileService: UserProfileService) {
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


}
