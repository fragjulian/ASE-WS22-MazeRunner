import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestUploadComponent } from './rest-upload.component';

describe('RestUploadComponent', () => {
  let component: RestUploadComponent;
  let fixture: ComponentFixture<RestUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestUploadComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
