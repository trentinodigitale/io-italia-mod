import { AfterViewInit, Component, ComponentFactoryResolver, Inject, ViewChild, ViewContainerRef } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { FormComponent } from '../form/form.component';


@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements AfterViewInit{

  @ViewChild('dynamicTag', { read: ViewContainerRef }) dynamicTag: ViewContainerRef;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
      this.dialogRef.disableClose = true; //make dialog modal
  }

  ngAfterViewInit() {
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.data.component);
    this.dynamicTag.clear();

    const dynamicComponent = <FormComponent<Object>>this.dynamicTag.createComponent(componentFactory).instance;

    dynamicComponent.data = this.data.input;
    dynamicComponent.closeEmitter.subscribe(
      data => {
        this.close();
      }
    );
    
  }

  close(): void {
    this.dialogRef.close();
  }


}