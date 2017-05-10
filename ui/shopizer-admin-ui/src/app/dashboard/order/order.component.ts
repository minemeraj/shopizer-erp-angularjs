import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import { Country } from '../../shared/objects/country';
import { Zone } from '../../shared/objects/zone';
import { Customer } from '../../shared/objects/customer';
import {ReferencesService,CustomerService} from '../../_services/index';
import { AlertService} from '../../_services/index';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'order-component',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.sass'],
  providers: [CustomerService,ReferencesService]
})
export class OrderComponent implements OnInit {

    errorMessage: String;
    customerId : string;
    submitted = false;
    active = true;
    
    orderForm : FormGroup;//form

    constructor(
        private referencesService : ReferencesService,
        private customerService : CustomerService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) { }
    
    ngOnInit() {}


}
