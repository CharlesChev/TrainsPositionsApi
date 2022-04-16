import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormEnginComponent } from './form-engin.component';

describe('FormEnginComponent', () => {
  let component: FormEnginComponent;
  let fixture: ComponentFixture<FormEnginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormEnginComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormEnginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
