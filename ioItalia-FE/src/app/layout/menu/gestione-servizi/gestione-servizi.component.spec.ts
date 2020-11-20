import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneServiziComponent } from './gestione-servizi.component';

describe('GestioneServiziComponent', () => {
  let component: GestioneServiziComponent;
  let fixture: ComponentFixture<GestioneServiziComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestioneServiziComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestioneServiziComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
