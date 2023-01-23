import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavbarComponent} from './navbar/navbar.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {AboutComponent} from './about/about.component';
import {FooterComponent} from './footer/footer.component';
import {MazeBuilderComponent} from './maze-builder/maze-builder.component';
import {FormsModule} from "@angular/forms";
import {RestUploadComponent} from './rest-upload/rest-upload.component';
import {HttpClientModule} from '@angular/common/http';
import {NgxDropzoneModule} from 'ngx-dropzone';
import {ColorPickerModule} from 'ngx-color-picker';
import {ColorpickerComponent} from './colorpicker/colorpicker.component';
import {MatTooltipModule} from '@angular/material/tooltip';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AboutComponent,
    FooterComponent,
    MazeBuilderComponent,
    RestUploadComponent,
    ColorpickerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatTooltipModule,
    FormsModule,
    HttpClientModule,
    FormsModule,
    NgxDropzoneModule,
    ColorPickerModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
