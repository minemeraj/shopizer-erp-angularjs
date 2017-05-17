import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import {RequestOptions, Request, RequestMethod} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import { Country } from '../shared/objects/country';
import { Zone } from '../shared/objects/zone';
import { OrderReferences } from '../shared/objects/orderReferences';
import { AuthenticationService } from './index';
import 'rxjs/add/operator/map'



@Injectable()
export class ReferencesService {

    constructor(
            private http: Http,
            private authenticationService: AuthenticationService) {
    }

    
    
    
    public getCountries = (lang: string): Observable<Country[]> => {
        
        let user = JSON.parse(localStorage.getItem('currentUser'));
    
        
        console.log('Before calling reference service');
        var params = 'lang=' + lang;
        
        let token = user.token;
        
        console.log("Setting token in reference service " + token);
        
        let headers = new Headers({ 'Authorization': token });
        let options = new RequestOptions({ headers: headers });
    
        return this.http.get(environment.baseUrl + '/api/countries?' + params, options)
            //.toPromise()
            //.then(response => response.json().data as Country[]);
            .map((resp: Response) => {
                //console.log(resp.json() as Country[])
                return resp.json() as Country[];
                //.map((response: Response) => <Country[]>response.json())
            //.catch(this.handleError);
            });

    }   

  
    public getOrderReferences = (lang: string): Observable<OrderReferences> => {
        
        let user = JSON.parse(localStorage.getItem('currentUser'));
    
        
        console.log('Before calling reference service order');
        var params = 'lang=' + lang;
        
        let token = user.token;
        
        console.log("Setting token in reference service " + token);
        
        let headers = new Headers({ 'Authorization': token });
        let options = new RequestOptions({ headers: headers });
        var url = environment.baseUrl + '/api/references/order/?' + params
        
        console.log('Order references url ' + url);
    
        return this.http.get(url, options)
            .map((resp: Response) => {
                return resp.json() as OrderReferences;
            });

    }
    
    public getZones = (code:string, lang: string): Observable<Zone[]> => {
        
        let user = JSON.parse(localStorage.getItem('currentUser'));
    
        
        console.log('Before calling reference service zone');
        var params = 'lang=' + lang;
        
        let token = user.token;
        
        console.log("Setting token in reference service " + token);
        
        let headers = new Headers({ 'Authorization': token });
        let options = new RequestOptions({ headers: headers });
        var url = environment.baseUrl + '/api/country/'+ code + '/zones?' + params
        
        console.log('Zone url ' + url);
    
        return this.http.get(url, options)
            .map((resp: Response) => {
                return resp.json() as Zone[];
            });

    }

}
