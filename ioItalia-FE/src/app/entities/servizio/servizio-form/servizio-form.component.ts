import { HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { FormComponent } from 'src/app/util/form/form.component';
import { SnackBarService } from 'src/app/util/snackbar/snackbar.service';

import { Servizio } from '../servizio.model';
import { ServizioService } from '../servizio.service';


@Component({
  selector: 'app-servizio-form',
  templateUrl: './servizio-form.component.html',
  styleUrls: ['./servizio-form.component.css', '../../../app.component.css']
})
export class ServizioFormComponent extends FormComponent<Servizio> {
  
  nomeServizio_length: string = "255";
  nomeEnte_length: string = "255";
  nomeDipartimento_length: string = "255";
  token_length: string = "255";
  codiceIdentificativo_length: string = "255";
  codiceIoItalia_length: string = "255";

  nomeElemento: string = "Servizio";


  constructor(
    protected formBuilder: FormBuilder,
    public dialog: MatDialog,
    protected snackbarService: SnackBarService,
    private servizioService: ServizioService,
  ) { 
    super(formBuilder, dialog, snackbarService);
  }

  
  initForm(): void {
    this.form = this.formBuilder.group({
      idObj: this.data.idObj,
      nomeServizio: [this.data.nomeServizio, [Validators.required]],
      nomeEnte: [this.data.nomeEnte, [Validators.required]],
      nomeDipartimento: this.data.nomeDipartimento,
      codiceFiscale: [this.data.codiceFiscale, [Validators.required]],
      codiceIdentificativo: [this.data.codiceIdentificativo, [Validators.required]],
      codiceServizioIoItalia: [this.data.codiceServizioIoItalia, [Validators.required]],
      email: [this.data.email, [Validators.required]],
      emailPec: [this.data.emailPec, [Validators.required]],
      tokenIoItalia: this.data.tokenIoItalia,
      version: this.data.version
    });

    if (this.data.idObj != undefined) { //opening servizio, not creating a new one
      this.form.disable();
    }
  }

  // confirmed to delete the area
  confirmedDelete() {
    this.servizioService.delete(this.data.idObj).subscribe(
      (res: HttpResponse<any>) => {
        this.onDeleteSuccess(this.nomeElemento);
      }
    );
  }


  save(formServizio: Servizio) {
    if(formServizio.idObj != undefined) { //update servizio
      this.servizioService.update(formServizio).subscribe(
        (res: HttpResponse<Servizio>) => {
          this.onEditSuccess(this.nomeElemento, res.body)
        }
      );
    } else { //new servizio
      this.servizioService.create(formServizio).subscribe(
        (res: HttpResponse<Servizio>) => {          
          this.onCreateSuccess(this.nomeElemento, res.body);
        }
      );
    }
  }


}
