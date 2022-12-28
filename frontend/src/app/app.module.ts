import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MrUploadFileComponent } from './upload/mr-upload-file.component';
import {DxFileUploaderModule, DxProgressBarModule} from "devextreme-angular";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './navbar/navbar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import { AboutComponent } from './about/about.component';
import { FooterComponent } from './footer/footer.component';
import { RestUploadComponent } from './rest-upload/rest-upload.component';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule} from "@angular/forms";
import { NgxDropzoneModule } from 'ngx-dropzone';
import { ColorPickerModule } from 'ngx-color-picker';
import { ColorpickerComponent } from './colorpicker/colorpicker.component';




@NgModule({
  declarations: [
    AppComponent,
    MrUploadFileComponent,
    NavbarComponent,
    AboutComponent,
    FooterComponent,
    RestUploadComponent,
    ColorpickerComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DxProgressBarModule,
    DxFileUploaderModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    HttpClientModule,
    FormsModule,
    NgxDropzoneModule,
    ColorPickerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
