import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GestioneServiziComponent } from './layout/menu/gestione-servizi/gestione-servizi.component';

const routes: Routes = [
  { path: 'servizi', component: GestioneServiziComponent }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
