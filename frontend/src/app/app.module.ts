import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MrNavbarComponent } from './navbar/mr-navbar.component';
import { MrUploadFileComponent } from './upload/mr-upload-file.component';
import {DxFileUploaderModule, DxProgressBarModule} from "devextreme-angular";

@NgModule({
  declarations: [
    AppComponent,
    MrNavbarComponent,
    MrUploadFileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DxProgressBarModule,
    DxFileUploaderModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
