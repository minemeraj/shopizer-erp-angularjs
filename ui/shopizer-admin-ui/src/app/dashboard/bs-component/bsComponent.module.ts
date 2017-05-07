import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import {
  AlertModule,
  ButtonsModule,
  BsDropdownModule,
  PaginationModule,
  ProgressbarModule,
  RatingModule,
  TabsModule,
  TooltipModule,
  TypeaheadModule
} from 'ng2-bootstrap';


import { BSComponentComponent } from './bsComponent.component';

@NgModule({
  imports: [
    RouterModule,
    FormsModule,
    CommonModule,
    AlertModule.forRoot(),
    ButtonsModule.forRoot(),
    BsDropdownModule.forRoot(),
    PaginationModule.forRoot(),
    ProgressbarModule.forRoot(),
    RatingModule.forRoot(),
    TabsModule.forRoot(),
    TooltipModule.forRoot(),
    TypeaheadModule.forRoot()
  ],
  declarations: [BSComponentComponent],
  exports: [BSComponentComponent]
})

export class BSComponentModule { }
