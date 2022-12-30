import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MazeBuilderComponent } from './maze-builder.component';

describe('MazeBuilderComponent', () => {
  let component: MazeBuilderComponent;
  let fixture: ComponentFixture<MazeBuilderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MazeBuilderComponent ]
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
