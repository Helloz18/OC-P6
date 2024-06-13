import { Routes } from '@angular/router';
import { HomeComponent } from './home.component';
import { TopicComponent } from '../topic/topic.component';

/**
 * In the HomeComponent, we can navigate to the routes defined in CORE_ROUTES
 */
export const HOME_ROUTES: Routes = [
  { path: '', component: HomeComponent }, 
  { path: '', loadChildren: () => import('src/app/core/core.routes').then(r => r.CORE_ROUTES) },
  { path: 'topics', component: TopicComponent}
   
];