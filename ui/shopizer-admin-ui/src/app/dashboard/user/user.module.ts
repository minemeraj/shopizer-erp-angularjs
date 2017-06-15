import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {AlertModule} from '../../_directives/alert.module';



import { UserComponent } from './user.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        AlertModule,
        BrowserModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [UserComponent],
    exports: [UserComponent]
})

export class UserModule { }
