import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarteFormContainerComponent } from './carte-form-container.component';

describe('CartFormContainerComponent', () => {
  let component: CarteFormContainerComponent;
  let fixture: ComponentFixture<CarteFormContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarteFormContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarteFormContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
