import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError} from "rxjs";
import {Position} from "./Position";

@Injectable({
  providedIn: 'root'
})
export class MazeBuilderAutoSolveService {

  constructor(private httpClient: HttpClient) {
  }

  downloadSolutionPath(sizeX: number, sizeY: number, walls: Set<Position>, obstacles: Set<Position>, startX: number, startY: number, goalX: number, goalY: number) {
    let returnObject = {
      walls: Array.from(walls.values()),
      obstacles: Array.from(obstacles.values())
    }
    return this.httpClient.post<Position[]>(`/api/maze/path?sizeX=${sizeX}&sizeY=${sizeY}&startX=${startX}&startY=${startY}&goalX=${goalX}&goalY=${goalY}`, returnObject, {
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
