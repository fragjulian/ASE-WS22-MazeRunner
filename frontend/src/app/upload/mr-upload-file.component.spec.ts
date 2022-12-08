import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MrUploadFileComponent } from './mr-upload-file.component';

describe('MrUploadFileComponent', () => {
  let component: MrUploadFileComponent;
  let fixture: ComponentFixture<MrUploadFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MrUploadFileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MrUploadFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
