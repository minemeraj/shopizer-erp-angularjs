import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import { Country } from '../../shared/objects/country';
import { Zone } from '../../shared/objects/zone';
import { User } from '../../shared/objects/user';
import { Order } from '../../shared/objects/order';
import {ReferencesService,UserService} from '../../_services/index';
import { AlertService} from '../../_services/index';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'user-component',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.sass'],
  providers: [UserService,ReferencesService]
})
export class UserComponent implements OnInit {
    
    
    permissions : string[] = [];
    errorMessage: String;
    userId : string;
    submitted = false;
    active = true;
    password : string;
    repeatPassword : string;
    isAdmin : boolean;
    
    userForm : FormGroup;//form
    
    user = new User();
    

    constructor(
        private referencesService : ReferencesService,
        private alertService: AlertService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) { }
    
    ngOnInit() {
        this.activatedRoute.params.subscribe((params: Params) => {
            let id = params['id'];
            if(id != null) {
                this.getUser(id);
            }
         });
        this.buildForm();

    }
    
    getUser(id : string) {
        
        this.userService.getById(id)
        .subscribe(
                user => {
                this.user = user;
                this.permissions = user.permissions;
                
                if(this.permissions.indexOf('admin') != -1) {
                    this.isAdmin = true;
                }

            }, //Bind to view
                        err => {
                    // Log errors if any
                    console.log(err);
            })
    }
    
    onSubmit(value: any, event: Event):void{
        event.preventDefault();
        this.submitted = true;
        this.user = value;
        this.user.password = this.password;
        this.user.permissions = this.permissions;
        
        if(this.user.id == null || this.user.id == '') {

            this.permissions.push('user');
            if(this.isAdmin == true) {
                this.permissions.push('admin');
            }
            this.user.permissions = this.permissions;
            this.user.password = this.password;
            console.log("******** User FORM SUBMITTED create ********");
            this.userService.create(this.user)
                    .subscribe(
                        user => {
                            this.userId = user.id;
                            console.log('Created user id ' + this.userId);
                        //console.log('this.countries.length=' + this.countries.length);
                        //console.log('this.countries[12].name=' + this.countries[12].name);
                    }, //Bind to view
    
                            error =>{ this.errorMessage = <any>error, console.log('Error while creating user ' + this.errorMessage)
                    })
                    //.subscribe( customer => this.customerId = customer.id,
                    //            error => this.errorMessage = <any>error
                    //           );
        } else {
            
            if(this.isAdmin == false) {
                var index = this.permissions.indexOf('admin');
                this.permissions.splice(index, 1);
            }
            this.userService.save(this.user)
            .subscribe(
                user => {
                    this.userId = user.id;

                    console.log('Saved user id ' + this.userId);
                //console.log('this.countries.length=' + this.countries.length);
                //console.log('this.countries[12].name=' + this.countries[12].name);
            }, //Bind to view

                    error =>{ this.errorMessage = <any>error, console.log('Error while saving user ' + this.errorMessage)
            })
            //.subscribe( customer => this.customerId = customer.id,
            //            error => this.errorMessage = <any>error
            //           );
        }
    }
    
    buildForm(): void {
        this.userForm = this.fb.group({
          'firstName': [this.user.firstName, [
              Validators.required
            ]
          ],
          'lastName': [this.user.lastName, [
               Validators.required                                  
            ]
          ],
          'userName': [this.user.userName, [
               Validators.required                                  
            ]
          ],
          'password': [this.user.password, [
                Validators.required                                  
             ]
           ]
        });
        this.userForm.valueChanges
        .subscribe(data => this.onValueChanged(data));
        this.onValueChanged(); // (re)set validation messages now
        //console.log("-- EXITING BUILD FORM --");
    }
    
    newUser():void {
        this.user = new User();
        this.userId = null;
        this.errorMessage = null;
    }
    
    onValueChanged(data?: any) {
        console.log('Value changed');
        if (!this.userForm) { return; }
        const form = this.userForm;
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
            'userName': '',
            'password': ''
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
