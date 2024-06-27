import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild, computed } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { TokenStorageService } from 'src/app/pages/auth/services/token-storage.service';

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

  @ViewChild('menu') menu!: ElementRef;
  @ViewChild('overlay') overlay!: ElementRef;

  
 constructor(private tokenService: TokenStorageService) {}

 /**
  * this method is for mobile menu
  * @param event 
  */
 openCloseMenu(event: Event) {
    const mymenu = this.menu.nativeElement;
    const myoverlay = this.overlay.nativeElement;
    if(mymenu.classList.contains('show')) {
    mymenu.classList.remove("show");
    myoverlay.classList.remove("overlay");
    event.stopPropagation();
  } else {
    mymenu.classList.add("show");
    myoverlay.classList.add("overlay");
    event.stopPropagation();
  }
 }
}
