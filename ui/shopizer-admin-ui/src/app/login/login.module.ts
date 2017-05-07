import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';
import { FormsModule } from '@angular/forms';
import {AlertModule} from '../_directives/alert.module';
//import { AlertComponent } from '../_directives/index';

@NgModule({
    imports: [CommonModule, RouterModule, FormsModule, AlertModule],
    declarations: [LoginComponent],
    exports: [LoginComponent]
})

export class LoginModule { }
