import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MazeBuilderComponent} from './maze-builder.component';
import {FormsModule} from "@angular/forms";
import {MazeBuilderAutoSolveService} from "../maze-builder-auto-solve.service";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('MazeBuilderComponent', () => {
  let component: MazeBuilderComponent;
  let fixture: ComponentFixture<MazeBuilderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MazeBuilderComponent],
      imports: [HttpClientTestingModule, FormsModule, MazeBuilderAutoSolveService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MazeBuilderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
