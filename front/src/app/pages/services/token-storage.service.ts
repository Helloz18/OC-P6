import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  constructor() {}

  /**
   * called at the deconnexion, clear the session storage of token.
   */
  signOut(): void {
    window.sessionStorage.clear();
  }

  /**
   * save the token in the session storage
   * @param token
   */
  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  /**
   * get the token from the session storage
   * @returns
   */
  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }
}
