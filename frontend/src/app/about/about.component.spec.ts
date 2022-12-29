import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AboutComponent} from './about.component';

describe('AboutComponent', () => {
  let component: AboutComponent;
  let fixture: ComponentFixture<AboutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AboutComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have existing header element', () => {
    const header = fixture.debugElement.nativeElement.querySelector('#about-header');
    expect(header).toBeTruthy();
  });

  it('heading should have "About Maze Runner" as text', () => {
    const heading = fixture.debugElement.nativeElement.querySelector('#about-header');
    expect(heading.innerHTML).toBe('About Maze Runner');
  });

  it('should have existing paragraph element for description', () => {
    const paragraph = fixture.debugElement.nativeElement.querySelector('#about-paragraph');
    expect(paragraph).toBeTruthy();
  });

  it('paragraph should have description of the program as text', () => {
    const paragraph = fixture.debugElement.nativeElement.querySelector('#about-paragraph');
    expect(paragraph.innerHTML).toBe(
      ' An algorithm for finding a way out of a maze uploaded as an image. The shortest path is then calculated on the server and the answer is returned as an image. Optionally, in a later stage we want to include a browser-based maze build tool for painting the maze. The shortest path should then be displayed directly in the build tool.'
    );
  });
});
