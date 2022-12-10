import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MrNavbarComponent } from './mr-navbar.component';

describe('MrNavbarComponent', () => {
  let component: MrNavbarComponent;
  let fixture: ComponentFixture<MrNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MrNavbarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MrNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
