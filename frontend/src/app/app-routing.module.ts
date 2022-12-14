import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {RestUploadComponent} from "./rest-upload/rest-upload.component";


const routes: Routes = [
  //default path
  {path: '', component: RestUploadComponent},
  //about path
  {path: 'about', component: AboutComponent},
  //home or upload path
  {path: 'home', component: RestUploadComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
