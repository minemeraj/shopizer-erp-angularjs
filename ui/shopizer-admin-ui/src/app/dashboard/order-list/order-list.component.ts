import { Component, OnInit } from '@angular/core';
import {OrderService} from '../../_services/index';
import { Order } from '../../shared/objects/order';
import { OrderList } from '../../shared/objects/orderList';


import * as $ from 'jquery';
import 'datatables.net'



@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: [
              './order-list.component.sass'
              ],
  providers: [OrderService]
})
export class OrderListComponent implements OnInit {
    


  constructor(private orderService: OrderService) { 
      
      
  }
  
  public tableWidget: any;

  public selectedName: string="";
  
  data: Order[];


  ngOnInit() {
      this.getOrders();
      
  }
  
  ngAfterViewInit() {
      this.initDatatable();
   }
  
  
  getOrders() {
      this.orderService.getAll()
      .subscribe(
          orderList => {
              this.data = orderList.content;
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
      let tableId: any = $('#order-list');
      this.tableWidget = tableId.DataTable({
        select: true,
        "lengthMenu": [ 50, 100, -1 ]
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


    public selectRow(index: number, row:any) {
      this.selectedName = "row#" + index + " " + row.name
    }

}
