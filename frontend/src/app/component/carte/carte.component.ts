import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import * as L from 'leaflet';
import 'leaflet-rotatedmarker';
import { EventService } from 'src/app/services/event.service';
import { MarkerSelectorService } from '../../services/marker-selector.service';

@Component({
  selector: 'app-carte',
  templateUrl: './carte.component.html',
  styleUrls: ['./carte.component.css'],
})
export class CarteComponent implements OnInit {
  constructor(
    private _snackBar: MatSnackBar,
    private markerSelector: MarkerSelectorService,
    private eventService: EventService
  ) {
    //MatSnackBar : injection popup d'erreur

    this.eventService.positionsEmitter.subscribe((inputPositions: any) => {
      this.updatePositions(inputPositions);
    });
  }

  @Input() positions: any; //reception des positions/message d'erreur envoyées par le composant formulaire

  public sncfStyle = L.tileLayer(
    'http://carto.sncf.fr/tiles/sncf/{z}/{x}/{y}.png',
    {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright',
      maxZoom: 20,
    }
  );
  public sncfEstompeStyle = L.tileLayer(
    'http://carto.sncf.fr/tiles/sncf-france/{z}/{x}/{y}.png',
    {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright',
      maxZoom: 20,
    }
  );
  public openstreetmapfrance = L.tileLayer(
    'http://carto.sncf.fr/tiles/osmfr/{z}/{x}/{y}.png',
    {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright',
      maxZoom: 20,
    }
  );
  public osmClassic = L.tileLayer(
    'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
    {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright',
      maxZoom: 20,
    }
  );

  public baseMaps = {
    'Osm Classique': this.osmClassic,
    'Sncf estompée': this.sncfEstompeStyle,
    'Voies OSM France': this.openstreetmapfrance,
    'SNCF Style': this.sncfStyle,
  };

  public map!: L.Map;

  openErrorPopup(message: string) {
    this._snackBar.open(message, 'Fermer', {
      duration: 6000,
      verticalPosition: 'top',
    });
  }

  public displayLabelOnZoom() {
    let zoom: number = this.map.getZoom();
    console.log(this.map.getPanes().markerPane);
    if (zoom >= 14) {
    }
  }

  ngOnInit(): void {
    this.map = L.map('map', { layers: [this.osmClassic] })
      .setView([45.702256973524534, 4.429602042370401], 16)
      .on('zoomend', () => {
        this.displayLabelOnZoom();
      });
    this.osmClassic.addTo(this.map);
    L.control.layers(this.baseMaps).addTo(this.map);
  }

  // https://softwareengineering.stackexchange.com/questions/18454/should-i-return-from-a-function-early-or-use-an-if-statement
  updatePositions(inputPositions: any) {
    if (inputPositions != undefined) {
      if (inputPositions.message) {
        //si erreur un message est retrouné par l'api à la place des positions
        console.log(inputPositions);
        this.openErrorPopup(inputPositions.message);
      } else {
        console.log(inputPositions);
        for (let i = 0; i < inputPositions.length; i++) {
          this.markerSelector
            .markerSelector(inputPositions[i])
            //https://stackoverflow.com/questions/42364619/hide-tooltip-in-leaflet-for-a-zoom-range
            .bindTooltip(
              inputPositions[i].utc_observation.substr(0, 21) +
                ' | vitesse:' +
                (inputPositions[i].vitesse_instantanee * 3.6).toPrecision(5) +
                'km/h | retard: ' +
                inputPositions[i].ecart_horaire_sillon +
                ' sec',
              { permanent: true, className: 'marker-label' } // css dans styles.css
            )
            .addTo(this.map);
        }
      }
    }
  }
}
