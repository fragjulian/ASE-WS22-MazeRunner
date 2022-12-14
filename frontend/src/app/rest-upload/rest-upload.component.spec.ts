import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestUploadComponent } from './rest-upload.component';
import { HttpClientTestingModule} from '@angular/common/http/testing';
describe('RestUploadComponent', () => {
  let component: RestUploadComponent;
  let fixture: ComponentFixture<RestUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
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
