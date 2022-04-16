import { formatDate } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import {EnvGeom} from '../../custom-interface/env-geom';
import {envList, reqPrefix, mdp} from '../../config-files/conf';
import {ValidationService} from '../../services/validation.service';
import { EventService } from 'src/app/services/event.service';

@Component({
  selector: 'app-form-engin',
  templateUrl: './form-engin.component.html',
  styleUrls: ['../form-course/form-course.component.css']
})
export class FormEnginComponent {

  constructor(validService:ValidationService, private eventService: EventService) { }

  envs: EnvGeom[] = envList;

  unParcoursEngin: boolean = true;
  unePositionDunEngin: boolean = false;
  unParcoursPlusieursJour: boolean = false;

  numEngin: string = '';
  date: Date = new Date();
  startDate: Date = new Date();
  endDate: Date = new Date();
  dateTime: string = '';
  env: string = '';
  validationService:ValidationService = new ValidationService();

  numCaracterCheck: boolean = true;
  reqResult: any;

  onChange(event: any) {
    if (event.value === 'Un parcours') {
      this.unParcoursEngin = true;
      this.unePositionDunEngin = false;
      this.unParcoursPlusieursJour = false;
    } else if (event.value === 'Une position') {
      this.unParcoursEngin = false;
      this.unePositionDunEngin = true;
      this.unParcoursPlusieursJour = false;
    } else if (event.value === 'Un parcours plusieurs journées') {
      this.unParcoursEngin = false;
      this.unePositionDunEngin = false;
      this.unParcoursPlusieursJour = true;
    }
  }

  /*verif caractères spéciaux numEngin/numCourse */
  onNumChange(event: any) {
    this.numCaracterCheck = this.validationService.onNumChange(this.numEngin);
   }

  async emitReqResult() {
    this.eventService.positionsEmitter.emit(await this.reqResult);
  }

  onSubmit(): void {
    const reqHeaders = new Headers();
    reqHeaders.append('Authorization', 'Basic '+ mdp);
    let reqsuffix: string = '';

    if (this.unParcoursEngin === true) {
      reqsuffix ='parcoursEngin/' +this.env + '/' + this.numEngin + '/' + formatDate(this.date.toString(), 'yyyy-MM-dd', 'en-US');
    }
    if (this.unePositionDunEngin === true) {
      reqsuffix = 'position/engin/' + this.env + '/' + this.numEngin + '/' + formatDate(this.dateTime.toString(), 'yyyy-MM-dd HH:mm:ss', 'en-US');
    }
    if (this.unParcoursPlusieursJour === true) {
      reqsuffix = 'parcoursEngin/' + this.env + '/' + this.numEngin + '/' + formatDate(this.startDate.toString(), 'yyyy-MM-dd', 'en-US') + '/' + formatDate(this.endDate.toString(), 'yyyy-MM-dd', 'en-US');
    }

    this.reqResult = fetch(reqPrefix + reqsuffix, { headers: reqHeaders }).then(
      function (response) {
        return response.json();
      }
    );
    this.emitReqResult();
}

}
