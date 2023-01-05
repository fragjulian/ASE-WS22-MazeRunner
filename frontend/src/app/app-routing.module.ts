import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {RestUploadComponent} from "./rest-upload/rest-upload.component";
import {ColorpickerComponent} from "./colorpicker/colorpicker.component";
import {MazeBuilderComponent} from "./maze-builder/maze-builder.component";


const routes: Routes = [

  {path: '', component: RestUploadComponent},
  {path: 'builder', component: MazeBuilderComponent},
  {path: 'colorpicker', component: ColorpickerComponent},
  {path: 'about', component: AboutComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
