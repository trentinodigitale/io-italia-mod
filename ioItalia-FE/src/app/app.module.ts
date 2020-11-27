import { HttpClientModule } from '@angular/common/http';
import { ErrorHandler, NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OAuthModule } from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './auth/login/login.component';
import { ServizioFormComponent } from './entities/servizio/servizio-form/servizio-form.component';
import { ServizioComponent } from './entities/servizio/servizio-list/servizio-list.component';
import { ServizioService } from './entities/servizio/servizio.service';
import { GestioneServiziComponent } from './layout/menu/gestione-servizi/gestione-servizi.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { MaterialModule } from './material/material.module';
import { DialogComponent } from './util/dialog/dialog.component';
import { ErrorDialogComponent } from './util/error-dialog/error-dialog.component';
import { IoTrentinoErrorHandler } from './util/error-dialog/error-handler';
import { FormComponent } from './util/form/form.component';
import { SnackBarService } from './util/snackbar/snackbar.service';
import { YesNoDialogComponent } from './util/yes-no-dialog/yes-no-dialog.component';



@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ErrorDialogComponent,
    GestioneServiziComponent,
    ServizioComponent,
    ServizioFormComponent,
    DialogComponent,
    FormComponent,
    YesNoDialogComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: [environment.serverUrl + 'api'],
          sendAccessToken: true
      } 
    }) 
  ],
  providers: [
    ServizioService,
    SnackBarService,
    {
      provide: ErrorHandler,
      useClass: IoTrentinoErrorHandler
    },
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
