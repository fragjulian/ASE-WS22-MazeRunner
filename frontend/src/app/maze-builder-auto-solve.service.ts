import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Position} from "./maze-builder/maze-builder.component";
import {catchError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MazeBuilderAutoSolveService {

  constructor(private httpClient: HttpClient) {
  }

  downloadSolutionPath(sizeX: number, sizeY: number, walls: Set<Position>, obstacles: Set<Position>) {
    let returnObject = {
      walls: Array.from(walls.values()),
      obstacles: Array.from(obstacles.values())
    }
    return this.httpClient.post<Position[]>(`http://localhost:8081/api/maze/path?sizeX=${sizeX}&sizeY=${sizeY}`, returnObject, {
      observe: 'response',
      responseType: 'json'
    }).pipe(
      catchError((error: any) => {
        console.error(error);
        // Return the error message
        return error.error;
      })
    );

  }
}
