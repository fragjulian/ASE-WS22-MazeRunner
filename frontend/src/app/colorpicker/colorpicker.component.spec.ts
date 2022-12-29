import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColorpickerComponent } from './colorpicker.component';

describe('ColorpickerComponent', () => {
  let component: ColorpickerComponent;
  let fixture: ComponentFixture<ColorpickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ColorpickerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ColorpickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have "Click on the image to get the color of the wall and the obstacles." as text in heading', () => {
    const heading = fixture.debugElement.nativeElement.querySelector('#colorpicker-heading');
    expect(heading.innerHTML).toBe('Click on the image to get the color of the wall and the obstacles.');
  });

  it('should have body element', () => {
    const body = fixture.debugElement.nativeElement.querySelector('#colorpicker-body')
    expect(body).toBeTruthy();
  });

  it('colorpicker-body should have colorpicker-body-input as first child', () => {
    const colorpicker_body = fixture.debugElement.nativeElement.querySelector('#colorpicker-body');
    const colorpicker_body_input = fixture.debugElement.nativeElement.querySelector('#colorpicker-body-input');
    expect(colorpicker_body.firstChild).toBe(colorpicker_body_input);
  });

});
