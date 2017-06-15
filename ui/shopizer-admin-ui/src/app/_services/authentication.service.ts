/**
 * New typescript file
 */
import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import {RequestOptions, Request, RequestMethod} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import 'rxjs/add/operator/map'
 
@Injectable()
export class AuthenticationService {
    
    public token: string;
    constructor(private http: Http) { }
 
    login(username: string, password: string) {
        
        var params = 'username=' + username + '&password=' + password;
        
        let headers = new Headers({ 'Access-Control-Allow-Origin': '*' });
        let options = new RequestOptions({ headers: headers });
        //header("Access-Control-Allow-Origin: *");
        //header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization, If-Modified-Since, Cache-Control, Pragma");
        //console.log('IN LOGIN ' + params);
        //return this.http.post(environment.baseUrl + '/login', JSON.stringify({ username: username, password: password }))
        //return this.http.post(environment.baseUrl + '/login', body)
        return this.http.post(environment.baseUrl + '/login?' + params,'',options)
            .map((response: Response) => {
                // login successful if there's a jwt token in the response
                localStorage.removeItem('currentUser');
                console.log("LOGIN RESPONSE " + response);
                let user = response.json();
                console.log("USER " + user);
                let token = response.json().token
                this.token = token;
                console.log('User -> ' + user.firstName + ' token -> ' + this.token);
                if (user && token) {
                    console.log('yes setting currentUser with token ' + token);
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    //localStorage.setItem('currentUser', JSON.stringify(user));
                    localStorage.setItem('currentUser', JSON.stringify({ username: user.username, firstName: user.firstName, lastName: user.lastName, permissions: user.permissions, token: token }));
                }
          });
    }
 
    logout() {
        // remove user from local storage to log user out
        console.log('Loggin out');
        localStorage.removeItem('currentUser');
    }
}