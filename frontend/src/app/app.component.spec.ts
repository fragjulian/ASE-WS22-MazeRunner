import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import {MrUploadFileComponent} from "./upload/mr-upload-file.component";
import {DxFileUploaderModule, DxProgressBarModule} from "devextreme-angular";
import {NavbarComponent} from "./navbar/navbar.component";
import {FooterComponent} from "./footer/footer.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        DxProgressBarModule,
        DxFileUploaderModule,
        MatToolbarModule,
        HttpClientModule,
        FormsModule
      ],
      declarations: [
        AppComponent,
        MrUploadFileComponent,
        NavbarComponent,
        FooterComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
