import {TestBed} from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {MazeBuilderAutoSolveService} from './maze-builder-auto-solve.service';

describe('MazeBuilderAutoSolveService', () => {
  let service: MazeBuilderAutoSolveService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(MazeBuilderAutoSolveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
