import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormsModule} from "@angular/forms";

import {RestUploadComponent} from './rest-upload.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {NgxDropzoneModule} from "ngx-dropzone";

describe('RestUploadComponent', () => {
  let component: RestUploadComponent;
  let fixture: ComponentFixture<RestUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,
        NgxDropzoneModule,
        FormsModule],
      declarations: [RestUploadComponent]
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
