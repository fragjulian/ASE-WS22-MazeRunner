import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {RestUploadComponent} from "./rest-upload/rest-upload.component";
import {ColorpickerComponent} from "./colorpicker/colorpicker.component";


const routes: Routes = [
  //default path
  {path: '', component: RestUploadComponent},
  //about path
  {path: 'about', component: AboutComponent},
  { path: '', component: ColorpickerComponent, outlet:'secondary' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
