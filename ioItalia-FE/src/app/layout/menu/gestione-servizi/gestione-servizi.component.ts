import {HttpResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material/paginator';
import {Servizio, ServizioPaginato} from 'src/app/entities/servizio/servizio.model';
import {ServizioService} from 'src/app/entities/servizio/servizio.service';

@Component({
  selector: 'app-gestione-servizi',
  templateUrl: './gestione-servizi.component.html',
  styleUrls: ['./gestione-servizi.component.css', '../../../app.component.css']
})
export class GestioneServiziComponent implements OnInit {

  servizi: Servizio[];
  servizio: Servizio;

  pageSize: number;
  pageNo: number;
  totalItems: number;

  constructor(
    private servizioService: ServizioService
  ) {
  }

  ngOnInit(): void {
    this.pageSize = 10;
    this.pageNo = 0;
    this.loadAll();
  }

  onSelection(event: Servizio) {
    console.log('selection ' + event.codiceFiscale);
    this.servizio = event;
  }

  loadAll() {
       this.servizioService.getServizi(this.pageNo, this.pageSize, undefined).subscribe(
      (res: HttpResponse<ServizioPaginato[]>) => {
        const serviziPaginati = Object.assign(res.body);
        this.servizi = serviziPaginati.servizioDTOs;
        this.totalItems = serviziPaginati.totalItems;
        this.servizio = undefined;
      }
    );
  }

  onPageUpdate(event: PageEvent) {
    this.pageNo = event.pageIndex;
    this.pageSize = event.pageSize;
    console.log('event ' + this.pageNo);
    this.loadAll();
  }

}
