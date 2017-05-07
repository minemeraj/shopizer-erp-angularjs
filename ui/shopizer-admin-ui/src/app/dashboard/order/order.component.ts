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

    selectedCountry:Country = new Country('CA', 'Canada');
    selectedZone:Zone = new Zone('QC', 'Quebec');
    errorMessage: String;
    countries: Country[];
    zones: Zone[];
    customerId : string;
    showProvincesList = true;
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
