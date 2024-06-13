import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from 'src/app/app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptorProviders } from './app/core/interceptors/auth.interceptor';

bootstrapApplication(AppComponent, {
  
  providers:
     [
      provideRouter(routes),
      provideAnimations(),
      provideHttpClient(withInterceptors([authInterceptorProviders])),
      
  ]
}).catch(err => console.error(err));
;