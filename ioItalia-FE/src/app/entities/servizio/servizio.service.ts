import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Servizio, ServizioPaginato } from './servizio.model';


@Injectable({
  providedIn: 'root'
})
export class ServizioService {

  private resourceUrl =  environment.serverUrl + 'api/v1/servizio';

  constructor(private http: HttpClient) { }

  get(id: number): Observable<HttpResponse<Servizio>> {
    return this.http.get<Servizio>(this.resourceUrl + '/' + id, { observe: 'response' });
  }

  getServizi(pageNo: number, pageSize: number, sortBy: string): Observable<HttpResponse<ServizioPaginato[]>> {
    
    let param = new HttpParams();

    if (pageNo != null) {
      param = param.append('pageNo', pageNo.toString());
    }
    if (pageSize != null) {
        param = param.append('pageSize', pageSize.toString());
    }
    if (sortBy != null) {
        param = param.append('sortBy', sortBy);
    }
    
    return this.http.get<ServizioPaginato[]>(this.resourceUrl + '/find', { observe: 'response', params: param });
  }

  update(servizio: Servizio): Observable<HttpResponse<Servizio>> {
    console.log("update");
    return this.http.put<Servizio>(this.resourceUrl, servizio, { observe: 'response' });
  }

  create(servizio: Servizio): Observable<HttpResponse<Servizio>> {
    console.log("create");
    return this.http.post<Servizio>(this.resourceUrl, servizio, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<Servizio>> {
    return this.http.delete<any>(this.resourceUrl + '/' + id, { observe: 'response' });
  }


}
