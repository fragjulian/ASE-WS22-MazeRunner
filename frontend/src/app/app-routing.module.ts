import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {MrUploadFileComponent} from "./upload/mr-upload-file.component";



const routes: Routes = [
  //default path
  {path: '', component: MrUploadFileComponent},
  //about path
  {path: 'about', component: AboutComponent},
  //home or upload path
  {path: 'home', component: MrUploadFileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
