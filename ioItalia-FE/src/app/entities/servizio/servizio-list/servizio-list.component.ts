import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSelectionList, MatSelectionListChange } from '@angular/material/list';
import { Dialog_size } from 'src/app/util/contants';
import { DialogComponent } from 'src/app/util/dialog/dialog.component';

import { ServizioFormComponent } from '../servizio-form/servizio-form.component';
import { Servizio } from '../servizio.model';


@Component({
  selector: 'app-servizio-list',
  templateUrl: './servizio-list.component.html',
  styleUrls: ['./servizio-list.component.css', '../../../app.component.css']
})
export class ServizioComponent implements OnInit {


  @Input()
  servizi: Servizio[];


  @ViewChild(MatSelectionList, { static: false })
  serviziSelector: MatSelectionList;

  @Output()
  selectedServizioEmitter: EventEmitter<Servizio> = new EventEmitter<Servizio>();

  @Output()
  refreshEmitter: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    public dialog: MatDialog
  ) { }

  ngOnInit() {

  }


  // handle mat selection list with only one option
  handleSelection(event: MatSelectionListChange) {
    this.selectedServizioEmitter.emit(event.option.value);
  }


  create(): void {
    this.serviziSelector.deselectAll();
    this.createDialog();
  }

  //create dialog for new element
  private createDialog(): void {
    let servizio = new Servizio();
    
    let dialogRef = this.dialog.open(DialogComponent, {
      width: Dialog_size.SMALL,
      data: { component: ServizioFormComponent, input: servizio }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.refreshEmitter.emit();
    });

  }
}
