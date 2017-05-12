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
    userName : string;
    changePassowrd = new ChangePassword();
    user = new User();
    submitted = false;
    active = true;
    
    userForm : FormGroup;//form
    

    constructor(
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private userService : UserService,
        private fb: FormBuilder
    ) { }
    
    ngOnInit() {
        
        let user = JSON.parse(localStorage.getItem('currentUser'));
        this.userName = user.username;
        this.changePassowrd.username(this.userName);
    }
    
    
    changePassword() {
        this.userService.changePassword(this.changePassowrd).subscribe(
                user => {
                    this.user = user;
                }, //Bind to view
                            err => {
                        // Log errors if any
                        console.log(err);
                })
    }
    


}
