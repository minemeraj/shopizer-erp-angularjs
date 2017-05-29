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
import { OrderTotal } from '../../shared/objects/orderTotal';
import {ReferencesService,CustomerService, OrderService, UserService} from '../../_services/index';
import { AlertService} from '../../_services/index';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'order-component',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.sass'],
  providers: [CustomerService, OrderService, ReferencesService, DatePipe, UserService]
})
export class OrderComponent implements OnInit {

    errorMessage: String;
    orderId : string;//submitted in the parameters
    
    submitted = false;
    orderSaved : string;
    
    active = true;
    orderNumber : string;//number of the order
    
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
    orderTotals : OrderTotal[] = [];
    users : User[] = [];
    
    orderForm : FormGroup;//form
    
    customer = new Customer();//reference customer
    
    order = new Order();//object in creation
    
    currentUser: User;
    

    constructor(
        private referencesService : ReferencesService,
        private customerService : CustomerService,
        private orderService : OrderService,
        private userService : UserService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private datePipe: DatePipe,
        private fb: FormBuilder

        
    ) { 
        this.date = new Date();
        this.minDate = new Date();
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
        this.order.status = 'CREATED';
        this.order.creator = this.currentUser.firstName + ' ' + this.currentUser.lastName;
        this.today = this.datePipe.transform(this.date, 'yyyy-MM-dd');
        //console.log('Today date ' + this.today);
        this.order.created = this.today;
    }
    
    ngOnInit() {
    
        this.order.customer = this.customer;
        
        //init order total
        this.total = '$0.00';
        this.activatedRoute.params.subscribe((params: Params) => {
            let customerId = params['customerId'];
            if(customerId != null) {
                console.log('Loading customer ' + customerId);
                this.getCustomer(customerId);
            }
            
            let orderId = params['id'];
            if(orderId != null) {
                this.getOrder(orderId);
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
            c.user = this.currentUser.firstName + ' ' + this.currentUser.lastName;
            c.comment = this.commentText;
            c.created = this.today;;
            this.orderComments.push(c);
            this.commentText = null;
        }
        
        
    }
    
    //order status
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
    
    //user list
    getUsers(): void  {
        
        this.userService.getAll()
        .subscribe(
            users => {
                this.users = users;
            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
        })
    }
    
    /**
     * Create an order number
     */
    getOrderNumber() {
        this.orderService.nextOrderId()
            .subscribe(
                    OrderId => {
                console.log('New order id ' + OrderId.value);
                this.orderNumber = OrderId.value;
                this.order.orderNumber = OrderId.value;

            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
            })
    }
    
    
    totalsChanged() {
        
        this.orderTotals = [];
        
        console.log('* Totals value has changed *');
        console.log('Price -> ' + this.price);
        console.log('Installation -> ' + this.installation);
        console.log('Deposit -> ' + this.deposit);
        
        if(this.price == null || this.price =='') {
            return;
        }
        
        var price = new OrderTotal();
        price.value = this.price;
        price.name = 'Sub total';
        price.type = 'SUBTOTAL';
        price.variation = 'ADD';
        this.orderTotals.push(price);
        
        if(this.installation != null && this.installation !='') {
            var installation = new OrderTotal();
            installation.value = this.installation;
            installation.name = 'Installation';
            installation.type = 'INSTALLATION';
            installation.variation = 'ADD';
            this.orderTotals.push(installation);
        }
        
        if(this.deposit != null && this.deposit !='') {
            var deposit = new OrderTotal();
            deposit.value = this.deposit;
            deposit.name = 'Deposit';
            deposit.type = 'DEPOSIT';
            deposit.variation ='SUBSTRACT';
            this.orderTotals.push(deposit);
        }
        
        this.orderService.total(this.orderTotals)
        .subscribe(
                total => {
                this.total = total.total;
                console.log('Total is ' + this.total);

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
                //let n = order.number.toString();
                this.orderNumber = order.orderNumber;
  
                this.customer = order.customer;
                this.orderComments = order.comments;
                
                if(this.orderComments == null) {
                    this.orderComments = [];
                }
  
                let orderTotals = order.orderTotals;
  
                    
                console.log('Get order -> ' + JSON.stringify(order));
                for (let i = 0; i < orderTotals.length; i++) {
                   console.log('Order total ' +  orderTotals[i].type + ' - ' + orderTotals[i].value);
                    //text += cars[i] + "<br>";
                   if(orderTotals[i].type == 'SUBTOTAL') {
                       this.price = orderTotals[i].value;
                   }
                   if(orderTotals[i].type == 'INSTALLATION') {
                       this.installation = orderTotals[i].value;
                   }
                   if(orderTotals[i].type == 'DEPOSIT') {
                       this.deposit = orderTotals[i].value;
                   }
                 }     

                 this.total = '$' + order.total;
                 this.totalsChanged();

            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
         })
    }
    
     onSubmit(value: any, event: Event):void{
        this.errorMessage = null;
        this.orderSaved = null;
        event.preventDefault();
        //console.log("******** FORM SUBMITTED ********");
        this.submitted = true;

        this.order.customer = this.customer;
        this.order.orderNumber = this.orderNumber;
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
        

        
        if(!this.isNumeric(this.price)) {
            this.errorMessage = 'Price should be numeric example 100 or 100.00';
            return;
        }
        
        if(this.installation != null && this.installation !='') {
            if(!this.isNumeric(this.installation)) {
                this.errorMessage = 'Installation should be numeric example 100 or 100.00';
                return;
            }
        }
        
        if(this.deposit != null && this.deposit !='') {
            if(!this.isNumeric(this.deposit)) {
                this.errorMessage = 'Deposit should be numeric example 100 or 100.00';
                return;
            }
        }
        
        if(this.order.description == null || this.order.description =='') {
            this.errorMessage = 'Order description is required';
            return;
        }
        
        this.order.orderTotals = this.orderTotals;
        
        console.log('Submited order -> ' + JSON.stringify(this.order));

        if(this.order.id == null || this.order.id == '') {
            this.orderService.create(this.order)
                    .subscribe(
                        order => {
                            this.orderId = order.id;
                            this.order.id = order.id;
                            console.log('Created order id ' + this.orderId);
                            this.orderSaved = this.order.created;
                    }, //Bind to view
    
                            error =>{ this.errorMessage = <any>error, console.log('Error while creating order ' + this.errorMessage)
                    })
        } else {
            this.order.modified = this.today;
            this.order.lastUpdator = this.currentUser.firstName + ' ' + this.currentUser.lastName;
            this.orderService.save(this.order)
            .subscribe(
                order => {
                    this.orderId = order.id;
                    console.log('Saved order id ' + this.orderId);
                    this.orderSaved = this.order.modified;
                }, //Bind to view

                    error =>{ this.errorMessage = <any>error, console.log('Error while saving order ' + this.errorMessage)
            })
        }
    }
     
    isNumeric(n): boolean {
         return !isNaN(parseFloat(n)) && isFinite(n);
    }
    
    buildForm(): void {
        this.orderForm = this.fb.group({
          'number': [this.order.orderNumber, [
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