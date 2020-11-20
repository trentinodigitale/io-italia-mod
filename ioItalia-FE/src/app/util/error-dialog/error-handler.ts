import { HttpErrorResponse } from '@angular/common/http';
import { ErrorHandler, Injectable, Injector, NgZone } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Dialog_size } from 'src/app/util/contants';

import { ErrorDialogComponent } from './error-dialog.component';


@Injectable({
    providedIn: 'root'
})
export class IoTrentinoErrorHandler implements ErrorHandler {

    private alreadyInError: boolean = false;


    constructor(
        //private authenticationService: AuthService,
        private injector: Injector,
        private ngZone: NgZone,
        public errorDialog: MatDialog
    ) { }

    handleError(error: Error | HttpErrorResponse) {
        if (error instanceof HttpErrorResponse) { //Backend returns unsuccessful response codes such as 404, 500 etc.	

            // if (error.status === 401 || error.status === 499) { //token expired -  auto logout if 401 response returned from api          
            //     // logout                
            //     this.authenticationService.logout();
            //     // redirect to Google Sign in
            //     let router = this.injector.get(Router);
            //     this.authenticationService.redirectToGoogle(router.url);
            //     return;
            // }

            // compose the error message
            let customMessage: string = "";
            if (error.status != 500) {
                
                if (error.error != undefined) { //if error message is passed into the returned object

                    let keys: string[] = Object.keys(error.error);
                    
                    console.log("Second error: " + error.error.error);
                    if (keys.find(k => k == "erroreImprevisto") != undefined) {
                        customMessage = error.error.erroreImprevisto;
                    } else if (keys.find(k => k == "error") != undefined) {
                        customMessage = error.error.error;
                    } else if (keys.find(k => k == "message") != undefined) {
                        customMessage = error.error.message;
                    }
                }
            }

            console.log(customMessage);
            // create the error dialog
            this.onError(customMessage);

        } else { //A client-side or network error occurred.	     
            console.error('An error occurred:', error);
        }
    }

    onError(error: string): void {
        this.ngZone.run(() => {
            if (!this.alreadyInError) {
                this.alreadyInError = true;
                error = error.replace(new RegExp('\n', 'g'), "<br>");
                const dialogRef = this.errorDialog.open(ErrorDialogComponent, {
                    width: Dialog_size.SMALL,
                    data: { title: "Attenzione", body: "L'operazione NON è andata a buon fine.<br><b>" + error + "</b>" }
                });

                dialogRef.afterClosed().subscribe(result => { 
                    this.alreadyInError = false;
                });

            }
        });
    }

}
