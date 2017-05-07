import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import {RequestOptions, Request, RequestMethod} from '@angular/http';
import { AuthenticationService } from './index';
import { Customer } from '../shared/objects/customer';
import { CustomerList } from '../shared/objects/customerList';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';


@Injectable()
export class CustomerService {


  private customers;
  constructor(
          private http: Http,
          private authenticationService: AuthenticationService) {}
  

  getAll = () : Observable<CustomerList> => {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/customers?page=0&size=5000'

      return this.http.get(url, options)
          .map((resp: Response) => {
              console.log('Response -> ' + resp.text())
              return resp.json() as CustomerList;
          });
      
  }
  
  getCustomer = (id : string) : Observable<Customer> => {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/customer/' + id;

      return this.http.get(url, options)
          .map((resp: Response) => {
              //console.log('Response -> ' + resp.text())
              return resp.json() as Customer;
          });
      
  }
  
  create(customer:Customer): Observable<Customer> {
      
      let user = JSON.parse(localStorage.getItem('currentUser'));

      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      console.log('USING CREATE CUSTOMER');
      
      return this.http.post(environment.baseUrl + '/api/customer', customer, options)
                  .map((resp: Response) => {
                      return resp.json() as Customer;
                  })
                 .catch(this.handleErrorObservable);
  }
  
  save(customer:Customer): Observable<Customer> {
      
      let user = JSON.parse(localStorage.getItem('currentUser'));

      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      console.log('USING UPDATE CUSTOMER');
      
      return this.http.put(environment.baseUrl + '/api/customer/' + customer.id, customer, options)
                  .map((resp: Response) => {
                      return resp.json() as Customer;
                  })
                 .catch(this.handleErrorObservable);
  }
  
  
  
  private handleErrorObservable (error: Response | any) {
      console.error(error.message || error);
      return Observable.throw(error.message || error);
  }

}
