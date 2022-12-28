import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {RestUploadComponent} from "./rest-upload/rest-upload.component";
import {ColorpickerComponent} from "./colorpicker/colorpicker.component";

const routes: Routes = [
  {
    path: "",
    children: [
      {path: "", component: RestUploadComponent},
      {path: "", component: ColorpickerComponent, outlet: "secondary"}
    ]
  },
  {path: 'about', component: AboutComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
