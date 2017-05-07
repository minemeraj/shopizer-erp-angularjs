import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {AlertModule} from '../../_directives/alert.module';



import { CustomerComponent } from './customer.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        AlertModule,
        BrowserModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [CustomerComponent],
    exports: [CustomerComponent]
})

export class CustomerModule { }
