import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MrUploadFileComponent } from './upload/mr-upload-file.component';
import {DxFileUploaderModule, DxProgressBarModule} from "devextreme-angular";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarNewComponent } from './navbar-new/navbar-new.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import { AboutComponent } from './about/about.component';


@NgModule({
  declarations: [
    AppComponent,
    MrUploadFileComponent,
    NavbarNewComponent,
    AboutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    DxProgressBarModule,
    DxFileUploaderModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
