import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import {MrNavbarComponent} from "./navbar/mr-navbar.component";
import {MrUploadFileComponent} from "./upload/mr-upload-file.component";
import {DxFileUploaderModule, DxProgressBarModule} from "devextreme-angular";

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        DxProgressBarModule,
        DxFileUploaderModule
      ],
      declarations: [
        AppComponent,
        MrNavbarComponent,
        MrUploadFileComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
