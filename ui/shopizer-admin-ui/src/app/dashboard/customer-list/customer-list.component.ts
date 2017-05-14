import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../../_services/index';
import { Customer } from '../../shared/objects/customer';
import { CustomerList } from '../../shared/objects/customerList';


import * as $ from 'jquery';
import 'datatables.net'



@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: [
              './customer-list.component.sass'
              ],
  providers: [CustomerService]
})
export class CustomerListComponent implements OnInit {
    


  constructor(private customerService: CustomerService) { 
      
      
  }
  
  public tableWidget: any;

  public selectedName: string="";
  
  data: Customer[];


  ngOnInit() {
      this.getCustomers();
      
  }
  
  ngAfterViewInit() {
      this.initDatatable();
   }
  
  
  getCustomers() {
      this.customerService.getAll()
      .subscribe(
          customerList => {
              this.data = customerList.content;
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
      let tableId: any = $('#customer-list');
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

    /**
    public addRow(): void {
      console.log('IN ADD ROW');
      if (this.data.length%5==0) {
        this.data.push({"id" : "123", "firstName": "Anna", "lastName": "Konda", "companyName" : "TT", "emailAddress" : "email", "phoneNumber" : "444-555-666", "address" : "123 this street", "city" : "eee", "country" : "ca", "zone" : "z", "postalCode" : "ffffff"})
      } else if (this.data.length%5==1) {
          this.data.push({"id" : "123", "firstName": "Anna", "lastName": "Konda", "companyName" : "TT", "emailAddress" : "email", "phoneNumber" : "444-555-666", "address" : "123 this street", "city" : "eee", "country" : "ca", "zone" : "z", "postalCode" : "ffffff"})
      } else if (this.data.length%5==2) {
          this.data.push({"id" : "123", "firstName": "Anna", "lastName": "Konda", "companyName" : "TT", "emailAddress" : "email", "phoneNumber" : "444-555-666", "address" : "123 this street", "city" : "eee", "country" : "ca", "zone" : "z", "postalCode" : "ffffff"})
      } else if (this.data.length%5==3) {
          this.data.push({"id" : "123", "firstName": "Anna", "lastName": "Konda", "companyName" : "TT", "emailAddress" : "email", "phoneNumber" : "444-555-666", "address" : "123 this street", "city" : "eee", "country" : "ca", "zone" : "z", "postalCode" : "ffffff"})
      } else {
        this.data.push({"id" : "123", "firstName": "Anna", "lastName": "Konda", "companyName" : "TT", "emailAddress" : "email", "phoneNumber" : "444-555-666", "address" : "123 this street", "city" : "eee", "country" : "ca", "zone" : "z", "postalCode" : "ffffff"})
     }
    
      this.reInitDatatable()
    }
     **/

    public selectRow(index: number, row:any) {
      this.selectedName = "row#" + index + " " + row.name
    }

}
