import { formatDate } from '@angular/common';
import { Component, Output, EventEmitter } from '@angular/core';
import {EnvGeom} from '../../custom-interface/env-geom';
import {envList, reqPrefix, mdp} from '../../config-files/conf';
import {ValidationService} from '../../services/validation.service';
import { EventService } from 'src/app/services/event.service';

@Component({
  selector: 'app-form-course',
  templateUrl: './form-course.component.html',
  styleUrls: ['./form-course.component.css'],
})

export class FormCourseComponent {
  constructor(private validationService:ValidationService, private eventService: EventService) {}

  uneCourse: boolean = true;
  unePositionDuneCourse: boolean = false;
  uneCoursePlusieursJour: boolean = false;

  numCourse: string = '';
  date: Date = new Date();
  startDate: Date = new Date();
  endDate: Date = new Date();
  dateTime: string = '';
  env: string = '';

  envs: EnvGeom[] = envList;

  numCaracterCheck: boolean = true;
  reqResult: any;

  onChange(event: any) {
    if (event.value === 'Une course') {
      this.uneCourse = true;
      this.unePositionDuneCourse = false;
      this.uneCoursePlusieursJour = false;
    } else if (event.value === 'Une position') {
      this.uneCourse = false;
      this.unePositionDuneCourse = true;
      this.uneCoursePlusieursJour = false;
    } else if (event.value === 'Plusieurs courses') {
      this.uneCourse = false;
      this.unePositionDuneCourse = false;
      this.uneCoursePlusieursJour = true;
    }
  }

  onNumChange(event: any) {
    const stringToCheck = this.numCourse;
    this.numCaracterCheck = this.validationService.onNumChange(stringToCheck);
   }

  async emitReqResult() {
    this.eventService.positionsEmitter.emit(await this.reqResult);
  }


  onSubmit(): void {
    const reqHeaders = new Headers();
    reqHeaders.append('Authorization', 'Basic '+ mdp);
    let reqsuffix: string = '';

    if (this.uneCourse === true) {
      reqsuffix ='course/' +this.env + '/' + this.numCourse + '/' + formatDate(this.date.toString(), 'yyyy-MM-dd', 'en-US');
    }
    if (this.unePositionDuneCourse === true) {
      reqsuffix = 'position/course/' + this.env + '/' + this.numCourse + '/' + formatDate(this.dateTime.toString(), 'yyyy-MM-dd HH:mm:ss', 'en-US');
    }
    if (this.uneCoursePlusieursJour === true) {
      reqsuffix = 'course/' + this.env + '/' + this.numCourse + '/' + formatDate(this.startDate.toString(), 'yyyy-MM-dd', 'en-US') + '/' + formatDate(this.endDate.toString(), 'yyyy-MM-dd', 'en-US');
    }

    this.reqResult = fetch(reqPrefix + reqsuffix, { headers: reqHeaders }).then(
      function (response) {
        return response.json();
      }
    );
    this.emitReqResult();//envoie du resultat de la requete au composant parent
  }
}
