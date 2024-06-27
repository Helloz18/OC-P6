import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { TokenStorageService } from "src/app/pages/auth/services/token-storage.service";


/**
 * guard that verify if a token is present to know if the user is connected 
 * and then has the authorization to navigate to some routes
 * @param route 
 * @param state 
 * @returns 
 */
export const authGuard: CanActivateFn = (route, state) => {
  
  const tokenService = inject(TokenStorageService);
  const router = inject(Router);

  if(tokenService.getToken()) {
    return true;
  } else {
    router.navigate(['/']);
    return false;
  }
}