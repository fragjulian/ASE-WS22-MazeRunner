// Import the platformBrowserDynamic function from the @angular/platform-browser-dynamic package
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

// Import the AppModule that contains the root component of the Angular app
import { AppModule } from './app/app.module';

// Use the platformBrowserDynamic function to bootstrap the AppModule
platformBrowserDynamic().bootstrapModule(AppModule)
  // If there is an error during bootstrapping, log the error to the console
  .catch(err => console.error(err));
