import { Routes } from '@angular/router';
import { RegisterComponent } from '../pages/register/register.component';
import { LoginComponent } from '../pages/login/login.component';
import { ShellComponent } from './shell/shell.component';
import { TopicComponent } from '../pages/topic/topic.component';

/**
 * ShellComponent will contain the header that will be displayed on every pages of the application
 * That's why routes such as login have to be in children of ShellComponent
 * The toolbar of the header will display some information in case of the user is connected
 */
export const CORE_ROUTES: Routes = [
  { path: '', component: ShellComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'topics', component: TopicComponent }
    ],
  },

 
];
