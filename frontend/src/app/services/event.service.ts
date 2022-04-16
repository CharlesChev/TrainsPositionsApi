import { EventEmitter, Injectable } from "@angular/core";

@Injectable({
    providedIn:  'root'
})
export class EventService {
    
    public positionsEmitter: EventEmitter<any> = new EventEmitter<any>();
}

