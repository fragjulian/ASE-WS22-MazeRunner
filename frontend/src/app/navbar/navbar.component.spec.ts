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

  it('last child of toolbar (buttons on the right) should have four child elements', () => {
    const last_child_of_toolbar = fixture.debugElement.nativeElement.querySelector('#navbar-toolbar').children[2];
    expect(last_child_of_toolbar.children[0].children.length).toBe(4);
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

  it('home button should link to home page', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-home');
    expect(btn.getAttribute('routerLink')).toBe('');
  });

  it('builder button should link to builder page', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-builder');
    expect(btn.getAttribute('routerLink')).toBe('builder');
  });

  it('colorpicker button should link to colorpicker page', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-colorpicker');
    expect(btn.getAttribute('routerLink')).toBe('colorpicker');
  });

  it('about button should link to about page', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-about');
    expect(btn.getAttribute('routerLink')).toBe('about');
  });

  it('home button should have correct uitestid attribute', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-home');
    expect(btn.getAttribute('uitestid')).toBe('nav-home');
  });

  it('builder button should have correct uitestid attribute', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-builder');
    expect(btn.getAttribute('uitestid')).toBe('nav-builder');
  });

  it('colorpicker button should have correct uitestid attribute', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-colorpicker');
    expect(btn.getAttribute('uitestid')).toBe('nav-colorpicker');
  });

  it('about button should have correct uitestid attribute', () => {
    const btn = fixture.debugElement.nativeElement.querySelector('#navbar-button-about');
    expect(btn.getAttribute('uitestid')).toBe('nav-about');
  });

  it('navbar should have primary color', () => {
    const navbar = fixture.debugElement.nativeElement.querySelector('#navbar-toolbar');
    expect(navbar.getAttribute('color')).toBe('primary');
  });

  it('navbar should have correct class', () => {
    const navbar = fixture.debugElement.nativeElement.querySelector('#navbar-toolbar');
    expect(navbar.classList).toContain('flex-container');
    expect(navbar.classList).toContain('mat-elevation-z4');
  });

});
