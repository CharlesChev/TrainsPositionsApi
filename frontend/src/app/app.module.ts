import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { CarteComponent } from './component/carte/carte.component';
import { FormCourseComponent } from './component/form-course/form-course.component';
import { CarteFormContainerComponent } from './component/carte-form-container/carte-form-container.component';
import { FormsModule } from '@angular/forms';

import {NgxMatDatetimePickerModule, NgxMatNativeDateModule, NgxMatTimepickerModule} from '@angular-material-components/datetime-picker';
import {MatRadioModule, MAT_RADIO_DEFAULT_OPTIONS} from '@angular/material/radio';
import {MatInputModule} from '@angular/material/input'; 
import {MatSelectModule} from '@angular/material/select'; 
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule } from '@angular/material/core';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTabsModule} from '@angular/material/tabs';
import { FormEnginComponent } from './component/form-engin/form-engin.component';


@NgModule({
  declarations: [
    AppComponent,
    CarteComponent,
    FormCourseComponent,
    CarteFormContainerComponent,
    FormCourseComponent,
    FormEnginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    NgxMatDatetimePickerModule,
    NgxMatNativeDateModule,
    NgxMatTimepickerModule,
    MatButtonModule,
    FormsModule,
    MatSnackBarModule,
    MatTabsModule
  ],
  providers: [{
    provide: MAT_RADIO_DEFAULT_OPTIONS,
    useValue: { color: 'primary' }
}],
  bootstrap: [AppComponent]
})
export class AppModule { }
