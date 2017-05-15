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
  selector: 'customer-component',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.sass'],
  providers: [CustomerService,ReferencesService]
})
export class CustomerComponent implements OnInit {

    selectedCountry:Country = new Country('CA', 'Canada');
    selectedZone:Zone = new Zone('QC', 'Quebec');
    errorMessage: String;
    countries: Country[];
    zones: Zone[];
    customerId : string;
    showProvincesList = true;
    submitted = false;
    active = true;
    
    customerForm : FormGroup;//form
    //customerForm: NgForm;
    customer = new Customer();//submitted customer

    constructor(
        private referencesService : ReferencesService,
        private customerService : CustomerService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private fb: FormBuilder
    ) { }
    
    getCountries(): void {
        this.referencesService.getCountries('fr')
            .subscribe(
                countries => {
                    this.countries = countries;
                    //console.log('this.countries=' + this.countries);
                    //console.log('this.countries.length=' + this.countries.length);
                    //console.log('this.countries[12].name=' + this.countries[12].name);
                }, //Bind to view
                            err => {
                        // Log errors if any
                        console.log(err);
                })
    }
    
    getZones(code:string): void {
        this.referencesService.getZones(code,'fr')
        .subscribe(
            zones => {
                this.zones = zones;
                if(zones != null && zones.length > 0) {
                    this.showProvincesList = true;
                } else {
                    this.showProvincesList = false;
                }
            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
            })        
    }

    ngOnInit() {
        this.activatedRoute.params.subscribe((params: Params) => {
            let id = params['id'];
            if(id != null) {
                this.getCustomer(id);
            }
         });
        
        //console.log('Init CustomerComponent');
        this.getCountries();
        
        //get the list of provinces
        this.getZones(this.selectedCountry.code);
        //http://stackoverflow.com/questions/40256658/getting-an-object-array-from-an-angularjs-2-service
        //console.log('Response from get countries ' + this.countries);
        this.buildForm();
    }
    
    buildForm(): void {
        this.customerForm = this.fb.group({
          'firstName': [this.customer.firstName, [
              Validators.required
            ]
          ],
          'lastName': [this.customer.lastName, [
               Validators.required                                  
            ]
          ],
          'emailAddress': [this.customer.emailAddress, [
               Validators.required                                  
            ]
          ],
          'address': [this.customer.address, [
               Validators.required                                  
             ]
          ],
          'city': [this.customer.city, [
               Validators.required                                  
             ]
          ],
          'postalCode': [this.customer.postalCode, [
               Validators.required                                  
             ]
          ],
          'zone': [this.customer.zone, [
                Validators.required                                  
              ]
           ],
           'phoneNumber': [this.customer.phoneNumber, [
                Validators.required                                  
              ]
           ]
        });
        this.customerForm.valueChanges
        .subscribe(data => this.onValueChanged(data));
        this.onValueChanged(); // (re)set validation messages now
        //console.log("-- EXITING BUILD FORM --");
    }
    
    onSelect(code) {
        //console.log('Selected country code ' + code);
        this.getZones(code);
    }
    
    onSubmit(value: any, event: Event):void{
        event.preventDefault();
        //console.log("******** FORM SUBMITTED ********");
        this.submitted = true;
        this.customer = value;
        //console.log('Customer id ' + this.customer.id);
        if(this.customer.id == null || this.customer.id == '') {
            this.customerService.create(this.customer)
                    .subscribe(
                        customer => {
                            this.customerId = customer.id;
                            console.log('Created customer id ' + this.customerId);
                        //console.log('this.countries.length=' + this.countries.length);
                        //console.log('this.countries[12].name=' + this.countries[12].name);
                    }, //Bind to view
    
                            error =>{ this.errorMessage = <any>error, console.log('Error while creating customer ' + this.errorMessage)
                    })
                    //.subscribe( customer => this.customerId = customer.id,
                    //            error => this.errorMessage = <any>error
                    //           );
        } else {
            this.customerService.save(this.customer)
            .subscribe(
                customer => {
                    this.customerId = customer.id;

                    console.log('Saved customer id ' + this.customerId);
                //console.log('this.countries.length=' + this.countries.length);
                //console.log('this.countries[12].name=' + this.countries[12].name);
            }, //Bind to view

                    error =>{ this.errorMessage = <any>error, console.log('Error while saving customer ' + this.errorMessage)
            })
            //.subscribe( customer => this.customerId = customer.id,
            //            error => this.errorMessage = <any>error
            //           );
        }
    }
    
    getCustomer(id : string) {
        
        this.customerService.getCustomer(id)
        .subscribe(
                customer => {
                this.customer = customer;
                this.selectedCountry = new Country(customer.country, customer.country);
                this.selectedZone = new Zone(customer.zone, customer.zone);
                this.customerId = customer.id;

            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
            })
    }
    
    newCustomer():void {
        this.customer = new Customer();
        this.customerId = null;
        this.errorMessage = null;
    }
    
    newOrder():void {
        console.log('Clicked on new order');
        this.router.navigateByUrl('/dashboard/order;customerId=' + this.customerId);
    }

    onValueChanged(data?: any) {
        console.log('Value changed');
        if (!this.customerForm) { return; }
        const form = this.customerForm;
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
