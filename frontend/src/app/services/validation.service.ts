import {Injectable} from "@angular/core";

@Injectable({

    providedIn:  'root'

})

export class ValidationService {

    public onNumChange(stringToCheck:string):boolean {
        if (stringToCheck.match('[!@#$%^&*(),.\'":{}|<>]')) {
            return  false;
        } else {
            return  true;
        }
      }
}

