import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import { User } from '../../shared/objects/user';
import {UserService} from '../../_services/index';
import { AlertService} from '../../_services/index';
import {Router, ActivatedRoute, Params} from '@angular/router';

import * as $ from 'jquery';
import 'datatables.net'

@Component({
  selector: 'user-list-component',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.sass'],
  providers: [UserService]
})
export class UserListComponent implements OnInit {

    errorMessage: String;
    public tableWidget: any;
    public selectedName: string="";
    
    data: User[];
    

    constructor(
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private userService : UserService,
        private fb: FormBuilder
    ) { }
    
    ngOnInit() {
    
    	this.getUsers();

    }
    
    ngAfterViewInit() {
      this.initDatatable();
    }
    
    getUsers() {
      this.userService.getAll()
      .subscribe(
          users => {
              this.data = users;
              //console.log('this.customers=' + this.data);
              //console.log('this.customers.length=' + this.customers.length);
              //console.log('this.countries[12].name=' + this.countries[12].name);
              this.reInitDatatable();
          }, //Bind to view
                      err => {
                  // Log errors if any
                  console.log(err);
          })
    }
    
    private initDatatable(): void {
      //debugger
      let tableId: any = $('#user-list');
      this.tableWidget = tableId.DataTable({
        select: true
      });
    }

    private reInitDatatable(): void {
      if (this.tableWidget) {
        this.tableWidget.destroy()
        this.tableWidget=null
      }
      setTimeout(() => this.initDatatable(),0)
    }

    public deleteRow(): void {
      //this.customers.pop();
      this.data.pop();
      this.reInitDatatable()
    }
    


}
