import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MrUploadFileComponent} from './mr-upload-file.component';
import {DxFileUploaderModule, DxProgressBarModule} from "devextreme-angular";

describe('MrUploadFileComponent', () => {
  let component: MrUploadFileComponent;
  let fixture: ComponentFixture<MrUploadFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        DxProgressBarModule,
        DxFileUploaderModule
      ],
      declarations: [
        MrUploadFileComponent
      ]
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
