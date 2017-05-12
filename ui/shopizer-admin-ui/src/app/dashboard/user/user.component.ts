import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import { Country } from '../../shared/objects/country';
import { Zone } from '../../shared/objects/zone';
import { Customer } from '../../shared/objects/customer';
import { Order } from '../../shared/objects/order';
import {ReferencesService,CustomerService, OrderService} from '../../_services/index';
import { AlertService} from '../../_services/index';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'user-component',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.sass'],
  providers: [CustomerService,ReferencesService]
})
export class UserComponent implements OnInit {

    errorMessage: String;
    customerId : string;
    submitted = false;
    active = true;
    
    userForm : FormGroup;//form
    

    constructor(
        private referencesService : ReferencesService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) { }
    
    ngOnInit() {

    }
    


}
