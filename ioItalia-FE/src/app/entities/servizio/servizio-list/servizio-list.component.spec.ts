import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServizioComponent } from './servizio-list.component';

describe('ServizioComponent', () => {
  let component: ServizioComponent;
  let fixture: ComponentFixture<ServizioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServizioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServizioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
