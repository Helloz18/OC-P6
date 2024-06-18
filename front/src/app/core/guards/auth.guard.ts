import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { TokenStorageService } from "src/app/pages/auth/services/token-storage.service";


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