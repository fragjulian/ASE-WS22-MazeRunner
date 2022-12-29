import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterComponent } from './footer.component';

describe('FooterComponent', () => {
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FooterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have existing footer element', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer');
    expect(footer).toBeTruthy();
  });

  it('should have existing left footer element', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer-left');
    expect(footer).toBeTruthy();
  });

  it('should have existing right footer element', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer-right');
    expect(footer).toBeTruthy();
  });

  it('should have existing left footer span element', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer-left-span');
    expect(footer).toBeTruthy();
  });

  it('should have existing right footer span element', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer-right-span');
    expect(footer).toBeTruthy();
  });

  it('should have existing left footer span link element', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer-left-span-link');
    expect(footer).toBeTruthy();
  });

  it('should have existing right footer span link element', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer-right-span-link');
    expect(footer).toBeTruthy();
  });

  it('footer should have two child elements', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer');
    expect(footer.children.length).toBe(2);
  });

  it('footer should have left footer element as first child', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer');
    const footer_left = fixture.debugElement.nativeElement.querySelector('#footer-left');
    expect(footer.children[0]).toBe(footer_left);
  });

  it('footer should have right footer element as second child', () => {
    const footer = fixture.debugElement.nativeElement.querySelector('#footer');
    const footer_right = fixture.debugElement.nativeElement.querySelector('#footer-right');
    expect(footer.children[1]).toBe(footer_right);
  });

  it('footer-left should have footer-left-span element as child', () => {
    const footer_left = fixture.debugElement.nativeElement.querySelector('#footer-left');
    const footer_left_span = fixture.debugElement.nativeElement.querySelector('#footer-left-span');
    expect(footer_left.children[0]).toBe(footer_left_span);
  });

  it('footer-right should have footer-right-span element as child', () => {
    const footer_right = fixture.debugElement.nativeElement.querySelector('#footer-right');
    const footer_right_span = fixture.debugElement.nativeElement.querySelector('#footer-right-span');
    expect(footer_right.children[0]).toBe(footer_right_span);
  });

  it('footer-left-span should have footer-left-span-link element as child', () => {
    const footer_left_span = fixture.debugElement.nativeElement.querySelector('#footer-left-span');
    const footer_left_span_link = fixture.debugElement.nativeElement.querySelector('#footer-left-span-link');
    expect(footer_left_span.children[0]).toBe(footer_left_span_link);
  });

  it('footer-right-span should have footer-right-span-link element as child', () => {
    const footer_right_span = fixture.debugElement.nativeElement.querySelector('#footer-right-span');
    const footer_right_span_link = fixture.debugElement.nativeElement.querySelector('#footer-right-span-link');
    expect(footer_right_span.children[0]).toBe(footer_right_span_link);
  });

  it('footer-left-span-link should have href of "https://www.flaticon.com/free-icons/maze"', () => {
    const footer_left_span_link = fixture.debugElement.nativeElement.querySelector('#footer-left-span-link');
    expect(footer_left_span_link.href).toBe("https://www.flaticon.com/free-icons/maze");
  });

  it('footer-right-span-link should have href of "https://www.aau.at/"', () => {
    const footer_right_span_link = fixture.debugElement.nativeElement.querySelector('#footer-right-span-link');
    expect(footer_right_span_link.href).toBe("https://www.aau.at/");
  });

  it('footer-left-span-link should have innerHtml of "Freepik"', () => {
    const footer_left_span_link = fixture.debugElement.nativeElement.querySelector('#footer-left-span-link');
    expect(footer_left_span_link.innerHTML).toBe("Freepik");
  });

  it('footer-right-span-link should have innerHtml of "University of Klagenfurt"', () => {
    const footer_right_span_link = fixture.debugElement.nativeElement.querySelector('#footer-right-span-link');
    expect(footer_right_span_link.innerHTML).toBe("University of Klagenfurt");
  });

  it('footer-left-span should contain innerHtml of "© 2022/23 Maze Runner / Maze icon created by "', () => {
    const footer_left_span = fixture.debugElement.nativeElement.querySelector('#footer-left-span');
    expect(footer_left_span.innerHTML).toContain("© 2022/23 Maze Runner / Maze icon created by ");
  });

  it('footer-right-span should contain innerHtml of " / Informatics / ASE"', () => {
    const footer_right_span = fixture.debugElement.nativeElement.querySelector('#footer-right-span');
    expect(footer_right_span.innerHTML).toContain(" / Informatics / ASE");
  });
});
