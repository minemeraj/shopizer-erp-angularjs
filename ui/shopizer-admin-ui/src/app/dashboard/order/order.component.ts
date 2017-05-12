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
  selector: 'order-component',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.sass'],
  providers: [CustomerService, OrderService, ReferencesService]
})
export class OrderComponent implements OnInit {

    errorMessage: String;
    customerId : string;
    submitted = false;
    active = true;
    
    orderForm : FormGroup;//form
    
    customer = new Customer();//reference customer
    order = new Order();//

    constructor(
        private referencesService : ReferencesService,
        private customerService : CustomerService,
        private orderService : OrderService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) { }
    
    ngOnInit() {
        
        this.activatedRoute.params.subscribe((params: Params) => {
            let customerId = params['customerId'];
            if(customerId != null) {
                //this.getCustomer(id);
            }
            
            let orderId = params['orderId'];
            if(orderId != null) {
                //this.getOrder(id);
            }
         });
        
    }
    
    getCustomer(id : string) {
        
        this.customerService.getCustomer(id)
        .subscribe(
                customer => {
                this.customer = customer;

            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
            })
    }
    
    
    getOrder(id : string) {
        
        this.orderService.getById(id)
        .subscribe(
                order => {
                this.order = order;

            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
            })
    }


}
