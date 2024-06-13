import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from 'src/app/app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';

bootstrapApplication(AppComponent, {
  
  providers:
     [
      provideRouter(routes),
      provideAnimations(),
      provideHttpClient()
  ]
}).catch(err => console.error(err));
;