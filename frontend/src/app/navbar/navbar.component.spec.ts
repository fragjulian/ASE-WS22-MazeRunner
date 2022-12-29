import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NavbarComponent} from './navbar.component';
import {MatToolbarModule} from "@angular/material/toolbar";

describe('NavbarNewComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MatToolbarModule],
      declarations: [NavbarComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('toolbar should exist', () => {
    const toolbar = fixture.debugElement.nativeElement.querySelector('#navbar-toolbar');
    expect(toolbar).toBeTruthy();
  });

  it('toolbar should have three child elements', () => {
    const toolbar = fixture.debugElement.nativeElement.querySelector('#navbar-toolbar');
    expect(toolbar.children.length).toBe(3);
  });

  it('last child of toolbar (buttons on the right) should have three child elements', () => {
    const last_child_of_toolbar = fixture.debugElement.nativeElement.querySelector('#navbar-toolbar').children[2];
    expect(last_child_of_toolbar.children.length).toBe(3);
  });

  it('should have existing logo element', () => {
    const logo = fixture.debugElement.nativeElement.querySelector('#navbar-logo');
    expect(logo).toBeTruthy();
  });

  it('logo width should be 40', () => {
    const logo = fixture.debugElement.nativeElement.querySelector('#navbar-logo');
    expect(logo.width).toBe(40);
  });

  it('logo height should be 40', () => {
    const logo = fixture.debugElement.nativeElement.querySelector('#navbar-logo');
    expect(logo.height).toBe(40);
  });

  it('should have existing title element', () => {
    const header = fixture.debugElement.nativeElement.querySelector('#navbar-header');
    expect(header).toBeTruthy();
  });

  it('should have as title "Maze Runner"', () => {
    const header = fixture.debugElement.nativeElement.querySelector('#navbar-header');
    expect(header.innerHTML).toBe('Maze Runner');
  });

  it('home button should exist', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-home');
    expect(btn).toBeTruthy();
  });

  it('about button should exist', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-about');
    expect(btn).toBeTruthy();
  });

  it('home button should have text "Home"', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-home');
    expect(btn.innerHTML).toBe('Home');
  });

  it('about button should have as text "About"', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-about');
    expect(btn.innerHTML).toBe('About');
  });

});
