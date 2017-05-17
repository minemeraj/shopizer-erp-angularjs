import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import { Country } from '../../shared/objects/country';
import { Zone } from '../../shared/objects/zone';
import { Customer } from '../../shared/objects/customer';
import { Order } from '../../shared/objects/order';
import { User } from '../../shared/objects/user';
import { OrderReferences } from '../../shared/objects/orderReferences';
import { KeyValue } from '../../shared/objects/keyValue';
import { OrderId } from '../../shared/objects/orderId';
import {ReferencesService,CustomerService, OrderService} from '../../_services/index';
import { AlertService} from '../../_services/index';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'order-component',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.sass'],
  providers: [CustomerService, OrderService, ReferencesService, DatePipe]
})
export class OrderComponent implements OnInit {

    errorMessage: String;
    orderId : string;
    submitted = false;
    active = true;
    orderNumber : number;
    price : string;
    installation : string;
    deposit : string;
    total : string;
    //today: number = Date.now();
    today : string
    date:Date;
    minDate:Date;
    disabledDate:{dt:Date,mode:string};
    orderReferences : OrderReferences;
    status : KeyValue[];
    
    orderForm : FormGroup;//form
    
    customer = new Customer();//reference customer
    order = new Order();//
    currentUser: User;
    

    constructor(
        private referencesService : ReferencesService,
        private customerService : CustomerService,
        private orderService : OrderService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private datePipe: DatePipe,
        private fb: FormBuilder

        
    ) { 
        this.date = new Date();
        this.minDate = new Date();
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
        //let datePipe = new DatePipe();
        //this.order.created = datePipe.transform(this.date, 'yyyy-MM-dd');
        this.order.status = 'CREATED';
        this.order.creator = this.currentUser.firstName + ' ' + this.currentUser.lastName;
        this.today = this.datePipe.transform(this.date, 'yyyy-MM-dd');
        console.log('Today date ' + this.today);
        this.order.created = this.today;
    }
    
    ngOnInit() {
    
        this.order.customer = this.customer;
        
        this.activatedRoute.params.subscribe((params: Params) => {
            let customerId = params['customerId'];
            if(customerId != null) {
                console.log('Loading customer ' + customerId);
                this.getCustomer(customerId);
            }
            
            let orderId = params['orderId'];
            if(orderId != null) {
                //this.getOrder(id);
            } else {
                console.log('Before get order number ');
                this.getOrderNumber();
            }
         });
        this.getReferences();
        
    }
    
     getReferences(): void {
        this.referencesService.getOrderReferences('fr')
            .subscribe(
                references => {
                    this.status = references.status;
                    //console.log('*** LOADED REFERENCES *** ' + this.status.length);
                }, //Bind to view
                            err => {
                        // Log errors if any
                        console.log(err);
                })
    }
    
    getOrderNumber() {
        this.orderService.nextOrderId()
            .subscribe(
                    OrderId => {
                this.orderNumber = OrderId.value;
                this.order.number = OrderId.value;

            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
            })
    }
    
    getCustomer(id : string) {
    
        console.log('Loading customer');
        
        this.customerService.getCustomer(id)
        .subscribe(
                customer => {
                this.customer = customer;
                this.order.customer = customer;

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
    
     onSubmit(value: any, event: Event):void{
        event.preventDefault();
        //console.log("******** FORM SUBMITTED ********");
        this.submitted = true;
        this.order = value;
        //console.log('Customer id ' + this.customer.id);
        if(this.order.id == null || this.order.id == '') {
            this.orderService.create(this.order)
                    .subscribe(
                        order => {
                            this.orderId = order.id;
                            console.log('Created ordr id ' + this.orderId);
                    }, //Bind to view
    
                            error =>{ this.errorMessage = <any>error, console.log('Error while creating order ' + this.errorMessage)
                    })
        } else {
            this.orderService.save(this.order)
            .subscribe(
                order => {
                    this.orderId = order.id;

                    console.log('Saved order id ' + this.orderId);
                //console.log('this.countries.length=' + this.countries.length);
                //console.log('this.countries[12].name=' + this.countries[12].name);
            }, //Bind to view

                    error =>{ this.errorMessage = <any>error, console.log('Error while saving order ' + this.errorMessage)
            })
            //.subscribe( customer => this.customerId = customer.id,
            //            error => this.errorMessage = <any>error
            //           );
        }
    }
    
    buildForm(): void {
        this.orderForm = this.fb.group({
          'number': [this.order.number, [
              Validators.required
            ]
          ],
          'created': [this.order.created, [
               Validators.required                                  
            ]
          ],
          'status': [this.order.status, [
               Validators.required                                  
            ]
          ],
          'creator': [this.order.creator, [
               Validators.required                                  
             ]
          ]
        });
        this.orderForm.valueChanges
        .subscribe(data => this.onValueChanged(data));
        this.onValueChanged(); // (re)set validation messages now
        //console.log("-- EXITING BUILD FORM --");
    }
    
        onValueChanged(data?: any) {
        console.log('Value changed');
        if (!this.orderForm) { return; }
        const form = this.orderForm;
        for (const field in this.formErrors) {
          // clear previous error message (if any)
          this.formErrors[field] = '';
          const control = form.get(field);
          if (control && control.dirty && !control.valid) {
            const messages = this.validationMessages[field];
            for (const key in control.errors) {
              this.formErrors[field] += messages[key] + ' ';
            }
          }
        }
      }
    
    formErrors = {
            'firstName': '',
            'lastName': '',
            'emailAddress': '',
            'address': '',
            'city': ''
     };
    
    validationMessages = {
            'firstName': {
              'required':      'First name is required.'
            },
            'lastName': {
              'required': 'Last name is required.'
            }
     };


}