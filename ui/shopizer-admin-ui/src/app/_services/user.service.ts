import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import {RequestOptions, Request, RequestMethod} from '@angular/http';
import { AuthenticationService } from './index';
import { User } from '../shared/objects/user';
import { ChangePassword } from '../shared/objects/changePassword';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';


@Injectable()
export class UserService {


  constructor(
          private http: Http,
          private authenticationService: AuthenticationService) {}
  
  
  changePassword = (changePassword : ChangePassword) : Observable<User> => {
      
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/user/password';

      return this.http.get(url, options)
          .map((resp: Response) => {
              console.log('Response -> ' + resp.text())
              return resp.json() as User;
          });
      
  }
  
 
  getAll = () : Observable<User[]> => {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/users'

      return this.http.get(url, options)
          .map((resp: Response) => {
              console.log('Response -> ' + resp.text())
              return resp.json() as User[];
          });
      
  }
  
  getById = (id : string) : Observable<User> => {
      let user = JSON.parse(localStorage.getItem('currentUser'));
      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      var url = environment.baseUrl + '/api/user/' + id;

      return this.http.get(url, options)
          .map((resp: Response) => {
              //console.log('Response -> ' + resp.text())
              return resp.json() as User;
          });
      
  }
  
  create(newUser : User): Observable<User> {
      
      let user = JSON.parse(localStorage.getItem('currentUser'));

      
      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      console.log('USING CREATE USER');
      
      return this.http.post(environment.baseUrl + '/api/user', newUser, options)
                  .map((resp: Response) => {
                      return resp.json() as User;
                  })
                 .catch(this.handleErrorObservable);
  }
  
  save(newUser:User): Observable<User> {
      
      let user = JSON.parse(localStorage.getItem('currentUser'));

      let token = user.token;
      
      let headers = new Headers({ 'Authorization': token });
      let options = new RequestOptions({ headers: headers });
      
      console.log('USING UPDATE ORDER');
      
      return this.http.put(environment.baseUrl + '/api/user/' + newUser.id, newUser, options)
                  .map((resp: Response) => {
                      return resp.json() as User;
                  })
                 .catch(this.handleErrorObservable);
  }
  
  
  
  private handleErrorObservable (error: Response | any) {
      console.error(error.message || error);
      return Observable.throw(error.message || error);
  }

}
