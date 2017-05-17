import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import {RequestOptions, Request, RequestMethod} from '@angular/http';
import { AuthenticationService } from './index';
import { Customer } from '../shared/objects/customer';
import { CustomerList } from '../shared/objects/customerList';
import { OrderList } from '../shared/objects/orderList';
import { OrderId } from '../shared/objects/orderId';
import { Order } from '../shared/objects/order';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';


@Injectable()
export class OrderService {


  constructor(
          private http: Http,
          private authenticationService: AuthenticationService) {}
  

  nextOrderId = () : Observable<OrderId> => {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/nextOrderId';

      return this.http.get(url, options)
          .map((resp: Response) => {
              console.log('Response -> ' + resp.text())
              return resp.json() as OrderId;
          });
      
  }
  
  getAll = () : Observable<OrderList> => {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/orders?page=0&size=5000'

      return this.http.get(url, options)
          .map((resp: Response) => {
              console.log('Response -> ' + resp.text())
              return resp.json() as OrderList;
          });
      
  }
  
  getById = (id : string) : Observable<Order> => {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/order/' + id;

      return this.http.get(url, options)
          .map((resp: Response) => {
              //console.log('Response -> ' + resp.text())
              return resp.json() as Order;
          });
      
  }
  
  create(order:Order): Observable<Order> {
      
      let user = JSON.parse(localStorage.getItem('currentUser'));

      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      console.log('USING CREATE ORDER');
      
      return this.http.post(environment.baseUrl + '/api/order', order, options)
                  .map((resp: Response) => {
                      return resp.json() as Customer;
                  })
                 .catch(this.handleErrorObservable);
  }
  
  save(order:Order): Observable<Order> {
      
      let user = JSON.parse(localStorage.getItem('currentUser'));

      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      console.log('USING UPDATE ORDER');
      
      return this.http.put(environment.baseUrl + '/api/order/' + order.id, order, options)
                  .map((resp: Response) => {
                      return resp.json() as Order;
                  })
                 .catch(this.handleErrorObservable);
  }
  
  
  
  private handleErrorObservable (error: Response | any) {
      console.error(error.message || error);
      return Observable.throw(error.message || error);
  }

}
