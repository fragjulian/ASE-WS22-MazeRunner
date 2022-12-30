import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {RestUploadComponent} from "./rest-upload/rest-upload.component";
import {ColorpickerComponent} from "./colorpicker/colorpicker.component";
import {MazeBuilderComponent} from "./maze-builder/maze-builder.component";


const routes: Routes = [

  {path: 'about', component: AboutComponent},
  {path: '', component: RestUploadComponent},
  {path: 'colorpicker', component: ColorpickerComponent},
  {path: 'builder', component: MazeBuilderComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
