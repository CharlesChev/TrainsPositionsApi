import {Injectable} from "@angular/core";
import * as L from 'leaflet';
import 'leaflet-rotatedmarker';

// https://angular.io/api/core/Injectable
@Injectable({
    providedIn: 'root'
})
export class MarkerSelectorService {

    public arrowIconSize:any =  [25, 30];
    public circleIconSize:any =  [25, 25];

    public highDelayAndDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/high_delay_triangle_.png',
        iconSize: this.arrowIconSize
    });

    public lowDelayAndDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/low_delay_triangle_.png',
        iconSize: this.arrowIconSize
    });

    public onTimeAndDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/ontime_triangle_.png',
        iconSize: this.arrowIconSize
    });

    public noDelayDataAndDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/nc_triangle_.png',
        iconSize: this.arrowIconSize
    });

    public noDelayDataAndNoDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/nc_cercle.png',
        iconSize: this.circleIconSize
    });

    public highDelayNoDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/high_delay_cercle.png',
        iconSize: this.circleIconSize
    });

    public lowDelayAndNoDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/low_delay_cercle.png',
        iconSize: this.circleIconSize
    });

    public onTimeAndNoDirectionIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/ontime_cercle.png',
        iconSize: this.circleIconSize
    });

    public otherIcon = L.icon({
        iconUrl:'../assets/icon_geopulse/neutral_cercle.png',
        iconSize: this.circleIconSize
    });

    public markerSelector(position:any): L.Marker<any>{
        if(position.ecart_horaire_sillon === null && position.cap_circulation === null){
            return L.marker([position.y, position.x],{icon: this.noDelayDataAndNoDirectionIcon});
        }else if (position.ecart_horaire_sillon < 501 && position.cap_circulation === null){
            return L.marker([position.y, position.x],{icon: this.onTimeAndNoDirectionIcon});
        }else if (position.ecart_horaire_sillon > 501 && position.ecart_horaire_sillon < 600 && position.cap_circulation === null){
            return L.marker([position.y, position.x],{icon: this.lowDelayAndNoDirectionIcon});
        }else if (position.ecart_horaire_sillon >= 600 && position.cap_circulation === null){
            return L.marker([position.y, position.x],{ icon: this.highDelayNoDirectionIcon})
        }else if (position.ecart_horaire_sillon === null && position.cap_circulation != null){
            return L.marker([position.y, position.x],{rotationAngle: position.cap_circulation +180, rotationOrigin: "center center", icon: this.noDelayDataAndDirectionIcon})
        }else if (position.ecart_horaire_sillon < 501 && position.cap_circulation != null){
            return L.marker([position.y, position.x],{rotationAngle: position.cap_circulation +180, rotationOrigin: "center center", icon: this.onTimeAndDirectionIcon});
        }else if (position.ecart_horaire_sillon > 501 && position.ecart_horaire_sillon < 600 && position.cap_circulation != null){
            return L.marker([position.y, position.x],{rotationAngle: position.cap_circulation +180, rotationOrigin: "center center", icon: this.lowDelayAndDirectionIcon});
        }else if (position.ecart_horaire_sillon >= 600 && position.cap_circulation != null){
            return L.marker([position.y, position.x],{rotationAngle: position.cap_circulation +180, rotationOrigin: "center center", icon: this.highDelayAndDirectionIcon})
        }else{
            return L.marker([position.y, position.x],{icon: this.otherIcon})
        }
    }
}