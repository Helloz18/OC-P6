import { Injectable, signal } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const LOGGED = 'logged';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})

export class TokenStorageService {

  /**
   * A valid password: 
   * - number of characters greater or equal to 8
   * - contains at least one of each of this types of character
   *    - a number
   *    - a lowercase letter
   *    - an uppercase letter
   *    - a special character
   */
  passwordPattern = '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$';

  //thanks to signal we can be updated in real time of the changes of this value
  loggedIn = signal(false);

  constructor() {
    if(window.sessionStorage.getItem(TOKEN_KEY)!= null) {
      this.loggedIn.set(true);
    }
  }
  /**
   * called at the deconnexion, clear the session storage of token.
   */
  signOut(): void {
    window.sessionStorage.clear();
    this.loggedIn.set(false);
  }

  /**
   * save the token in the session storage
   * @param token
   */
  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    this.loggedIn.set(true);
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
