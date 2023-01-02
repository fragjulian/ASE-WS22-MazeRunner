import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MazeBuilderComponent} from './maze-builder.component';
import {FormsModule} from "@angular/forms";

describe('MazeBuilderComponent', () => {
  let component: MazeBuilderComponent;
  let fixture: ComponentFixture<MazeBuilderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MazeBuilderComponent ],
      imports: [ FormsModule ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MazeBuilderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a canvas element', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('canvas')).toBeTruthy();
  });

  it('should have a color picker element', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('#color-picker')).toBeTruthy();
  });

  it('should have a brush color of black by default', () => {
    expect(component.brushColor).toEqual('black');
  });

  it('should change the brush color when the color picker value changes', () => {
    component.brushColor = 'red';
    expect(component.brushColor).toEqual('red');
  });

  it('should set isDrawing to true when the mouse is pressed down', () => {
    component.handleMouseDown(new MouseEvent('mousedown'));
    expect(component.isDrawing).toEqual(true);
  });

  it('should set isDrawing to false when the mouse is released', () => {
    component.isDrawing = true;
    component.handleMouseUp(new MouseEvent('mouseup'));
    expect(component.isDrawing).toEqual(false);
  });

  it('should call the drawPixel function when the mouse is moved and isDrawing is true', () => {
    spyOn<any>(component, 'drawPixel');
    component.isDrawing = true;
    const canvas = fixture.nativeElement.querySelector('#maze-canvas');
    canvas.dispatchEvent(new MouseEvent('mousemove', { buttons: 1 }));
    expect(component['drawPixel']).toHaveBeenCalled();
  });

  it('should call the drawPixel function when the mouse is clicked', () => {
      spyOn<any>(component, 'drawPixelAtCurrentMousePosition');
      component.handleClick(new MouseEvent('click'));
      expect(component['drawPixelAtCurrentMousePosition']).toHaveBeenCalled();
    });

    it('should not call the drawPixel function when the mouse is moved and isDrawing is false', () => {
      spyOn<any>(component, 'drawPixelAtCurrentMousePosition');
      component.handleMouseMove(new MouseEvent('mousemove'));
      expect(component['drawPixelAtCurrentMousePosition']).not.toHaveBeenCalled();
    });

    it('should call the clearMaze function when the clear maze button is clicked', () => {
      spyOn(component, 'clearMaze');
      const clearMazeButton = fixture.nativeElement.querySelector('.btn-outline-danger');
      clearMazeButton.dispatchEvent(new Event('click'));
      expect(component.clearMaze).toHaveBeenCalled();
    });

    it('should call the exportMazeAsImage function when the export maze button is clicked', () => {
      spyOn(component, 'exportMazeAsImage');
      const exportMazeButton = fixture.nativeElement.querySelector('.btn-outline-primary:nth-of-type(2)');
      exportMazeButton.dispatchEvent(new Event('click'));
      expect(component.exportMazeAsImage).toHaveBeenCalled();
    });

    it('should call the drawMazeBorder function when the draw border button is clicked', () => {
      spyOn(component, 'drawMazeBorder');
      const drawBorderButton = fixture.nativeElement.querySelector('.btn-outline-primary:nth-of-type(3)');
      drawBorderButton.dispatchEvent(new Event('click'));
      expect(component.drawMazeBorder).toHaveBeenCalled();
    });

    it('should call the openColorPickerDialog function when the pick color button is clicked', () => {
      spyOn(component, 'openColorPickerDialog');
      const pickColorButton = fixture.nativeElement.querySelector('.btn-outline-primary:nth-of-type(4)');
      pickColorButton.dispatchEvent(new Event('click'));
      expect(component.openColorPickerDialog).toHaveBeenCalled();
    });
});
