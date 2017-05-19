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
import { OrderComment } from '../../shared/objects/orderComment';
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
    orderNumber : number;//id of the order
    
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
    commentText : string;
    orderComments : OrderComment[] = [];
    
    orderForm : FormGroup;//form
    
    customer = new Customer();//reference customer
    
    order = new Order();//object in creation
    
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
                //populate formOrder
            } else {
                console.log('Before get order number ');
                this.getOrderNumber();
            }
         });
        this.getReferences();
        this.buildForm();
        
    }
    

    addComment():void {
        
        
        
        //add a OrderComment to order
        if(this.commentText != null && this.commentText != '') {
            //console.log('Comments are ' + this.commentText);
            var c = new OrderComment();
            c.user = this.order.creator;
            c.comment = this.commentText;
            c.created = this.today;;
            this.orderComments.push(c);
            this.commentText = null;
        }
        
        
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
        this.errorMessage = null;
        event.preventDefault();
        //console.log("******** FORM SUBMITTED ********");
        this.submitted = true;

        this.order.customer = this.customer;
        this.order.number = this.orderNumber;
        this.order.comments = this.orderComments;
         
        //console.log('Order -> ' + JSON.stringify(this.order));
        //console.log('Form valid ' + this.orderForm.valid);
         
        //validate prices
        //price is required
        if(this.price == null || this.price =='') {
            //console.log('price error');
            this.errorMessage = 'Price is required';
            return;
        }
        var regex  = /^\d+(?:\.\d{0,2})$/;
        //var numStr = "123.20";
        if (!regex.test(this.price)) {
            console.log('not numeric');
            this.errorMessage = 'Price is not numeric';
            return;
        }

        //if(this.order.id == null || this.order.id == '') {
        //    this.orderService.create(this.order)
        //            .subscribe(
        //                order => {
        //                    this.orderId = order.id;
        //                    console.log('Created ordr id ' + this.orderId);
        //            }, //Bind to view
    
        //                    error =>{ this.errorMessage = <any>error, console.log('Error while creating order ' + this.errorMessage)
        //            })
        //} else {
        //    this.orderService.save(this.order)
        //    .subscribe(
        //        order => {
         //           this.orderId = order.id;

         //           console.log('Saved order id ' + this.orderId);
         //   }, //Bind to view

          //          error =>{ this.errorMessage = <any>error, console.log('Error while saving order ' + this.errorMessage)
          //  })
        //}
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
          'estimated': [this.order.estimated, [
              Validators.required                                  
            ]
          ],
          'creator': [this.order.creator, [
               Validators.required                                  
             ]
          ],
          'price': [this.price, [
               Validators.required                                  
             ]
          ],
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
            },
             'price': {
              'required':      'Price is required.'
            }
     };


}