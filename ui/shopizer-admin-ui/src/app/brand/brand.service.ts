import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Brand } from './brand';
import 'rxjs/add/operator/map';


@Injectable()
export class BrandService {

  private serviceUrl : '${environment.baseUrl}/brand';
  constructor(private http: Http) {
  }
  
  get(code: string): Observable<Brand> {
    let brand$ = this.http
      .get('${this.serviceUrl}/brand/${code}', {headers: this.getHeaders()})
      .map(mapBrand);
      return brand$;
  }
  
  private getHeaders() {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');
    return headers;
  }
  
  

  

}

  function mapBrand(response: Response): Brand {
    return toBrand(response.json());
  }

  function toBrand(r: any): Brand {
    return r;
  }

