import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { Dialog_size } from '../contants';
import { SnackBarService } from '../snackbar/snackbar.service';
import { YesNoDialogComponent } from '../yes-no-dialog/yes-no-dialog.component';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent<E> implements OnInit, OnChanges {

  //elemento contenente i dati del form
  @Input()
  data: E;

  //se il form è all'interno del dialog, emette quando il dialog deve esserec hiuso
  @Output()
  closeEmitter: EventEmitter<void> = new EventEmitter<void>();

  //emette quando i dati devono essere aggiornati
  @Output()
  refreshEmitter: EventEmitter<void> = new EventEmitter<void>();

  form: FormGroup;

  constructor(
    protected formBuilder: FormBuilder,
    public dialog: MatDialog,
    protected snackbarService: SnackBarService
  ) { }

  ngOnInit() {
    this.initForm();
  }

  ngOnChanges(): void {
    this.initForm();
  }

  initForm() {}

  closeDialog() {
    this.closeEmitter.emit();
  }

  // clicked on edit icon
  edit(): void {
    this.form.enable();
  }

  // clicked on cancel button
  cancel(): void {
    this.closeDialog();
    this.initForm(); // cancel not yet saved updates - go back to previous situation
  }

  // clicked on delete icon
  delete(): void {
    this.createDeleteDialog();
  }
  

  // create dialog for delete
  private createDeleteDialog(): void {
    const dialogRef = this.dialog.open(YesNoDialogComponent, {
      width: Dialog_size.SMALL,
      data: {title: 'Attenzione', text: 'Stai per eliminare l\'elemento. Desideri continuare?'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // if the delation has been confirmed
        this.confirmedDelete();
      }
    });
  }

  confirmedDelete() {
    
  }

  onCreateSuccess(nomeElemento: string, elemento: E) {
    this.data = elemento;
    this.refreshEmitter.emit();
    this.snackbarService.openSnackBar(nomeElemento + ' creato correttamente');
    this.closeDialog();
  }

  onEditSuccess(nomeElemento: string, elemento: E) {
    this.data = elemento;
    this.form.disable(); //save updates and disable form
    this.initForm();
    this.refreshEmitter.emit();
    this.snackbarService.openSnackBar(nomeElemento + ' aggiornato correttamente');
  }

  onDeleteSuccess(nomeElemento: string) {
    this.refreshEmitter.emit();
    this.snackbarService.openSnackBar(nomeElemento + ' eliminato correttamente');
  }
  


}
