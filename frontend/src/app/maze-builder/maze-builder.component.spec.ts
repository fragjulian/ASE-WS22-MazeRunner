import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MazeBuilderComponent} from './maze-builder.component';
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('MazeBuilderComponent', () => {
  let component: MazeBuilderComponent;
  let fixture: ComponentFixture<MazeBuilderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MazeBuilderComponent ],
      imports: [FormsModule, HttpClientTestingModule]
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
