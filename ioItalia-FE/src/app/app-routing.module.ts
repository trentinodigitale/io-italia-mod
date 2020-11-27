import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './auth/login/login.component';
import { GestioneServiziComponent } from './layout/menu/gestione-servizi/gestione-servizi.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'servizi', component: GestioneServiziComponent, canActivate: [AuthGuard] }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
