import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { TokenStorageService } from "src/app/pages/services/token-storage.service";

export const authInterceptorProviders: HttpInterceptorFn = (req, next) => {
  const authToken = inject(TokenStorageService).getToken();

  if(authToken) {
  // Clone the request and add the authorization header
  const authReq = req.clone({
   // withCredentials: true,
    setHeaders: {
      Authorization: `Bearer ${authToken}`,
    }
  });
  
  console.log(authReq)

  // Pass the cloned request with the updated header to the next handler
  return next(authReq);
} else {
    return next(req);
  }
};
// provide to httpClient in main.ts the interceptor : provideHttpClient(withInterceptors([authInterceptorProviders])),