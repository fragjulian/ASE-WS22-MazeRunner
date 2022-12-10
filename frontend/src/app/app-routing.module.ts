import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {MrUploadFileComponent} from "./upload/mr-upload-file.component";

const routes: Routes = [
  {path: 'about', component: AboutComponent},
  {path: 'home', component: MrUploadFileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
