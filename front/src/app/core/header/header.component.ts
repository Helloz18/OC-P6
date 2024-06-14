import { CommonModule } from '@angular/common';
import { Component, computed } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { TokenStorageService } from 'src/app/pages/services/token-storage.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, MatToolbarModule, RouterModule, MatIconModule, MatButtonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
 
  // this will get the value of the signal loggedIn() and be informed thanks to computed for any change on this value
  loggedIn = computed(() => this.tokenService.loggedIn());
  
 constructor(private tokenService: TokenStorageService) {}
}
