import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import { User } from '../../shared/objects/user';
import { ChangePassword } from '../../shared/objects/changePassword';
import {UserService} from '../../_services/index';
import { AlertService} from '../../_services/index';
import {Router, ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'password-component',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.sass'],
  providers: [UserService]
})
export class PasswordComponent implements OnInit {

    errorMessage: String;
    confirm: String;
    userName : string;
    user = new User();
    submitted = false;
    active = true;
    
    changePasswordForm : FormGroup;//form
    changePassword = new ChangePassword();
    

    constructor(
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private userService : UserService,
        private router: Router,
        private fb: FormBuilder
    ) { }
    
    ngOnInit() {
        
        let user = JSON.parse(localStorage.getItem('currentUser'));
        this.userName = user.username;
        this.confirm = '';
        this.buildForm();
    }
    
    
    buildForm(): void {
        console.log("-- ENTERING BUILD FORM --");
        this.changePasswordForm = this.fb.group({
          'password': [this.changePassword.password, [
              Validators.required
            ]
          ],
          'newPassword': [this.changePassword.newPassword, [
               Validators.required                                  
            ]
          ],
          'repeatPassword': [this.changePassword.repeatPassword, [
               Validators.required                                  
            ]
          ]
        });
        //this.userForm.valueChanges
        //.subscribe(data => this.onValueChanged(data));
        //this.onValueChanged(); // (re)set validation messages now
        //console.log("-- EXITING BUILD FORM --");
    }
    
    
    onSubmit(value: any, event: Event):void{
        event.preventDefault();
        console.log("******** FORM SUBMITTED ********");
        this.submitted = true;
        this.submitChangePassword();
    }
      
    
    onValueChanged(data?: any) {
        console.log('Value changed');
        if (!this.changePasswordForm) { return; }
        const form = this.changePasswordForm;
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
            'password': '',
            'newPassword': '',
            'repeatPassword': ''
     };
    
    validationMessages = {
            'password': {
              'required': 'Password is required.'
            },
            'newPassword': {
              'required': 'New password is required.'
            },
            'repeatPassword': {
              'required': 'Repeat password is required.'
            }
     };
    
    
    submitChangePassword() {
        this.userService.changePassword(this.changePassword).subscribe(
                user => {
                    this.user = user;
                }, //Bind to view
                            err => {
                        // Log errors if any
                        console.log(err);
                })
    }
    


}
