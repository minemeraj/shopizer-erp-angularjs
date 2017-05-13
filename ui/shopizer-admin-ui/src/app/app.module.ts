import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { routes } from './app.routes';
import { LoginModule } from './login/login.module';
import { SignupModule } from './signup/signup.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { CustomerListModule } from './dashboard/customer-list/customer-list.module';
import { CustomerModule } from './dashboard/customer/customer.module';
import { UserModule } from './dashboard/user/user.module';
import { UserListModule } from './dashboard/user-list/user-list.module';
import { OrderModule } from './dashboard/order/order.module';
import { PasswordModule } from './dashboard/password/password.module';
import { AlertModule } from './_directives/alert.module';
import { AuthGuard } from './_guard/index';
import { AlertService, AuthenticationService, ReferencesService } from './_services/index';

//mocks to be removed
import { fakeBackendProvider } from './_helpers/index';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';
import { CustomerComponent} from './dashboard/customer/customer.component';
//import { AlertComponent } from './_directives/index';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(routes),
    LoginModule,
    UserModule,
    SignupModule,
    DashboardModule,
    CustomerListModule,
    UserListModule,
    CustomerModule,
    OrderModule,
    PasswordModule,
    AlertModule
  ],
  providers: [
        AuthGuard,
        AlertService,
        AuthenticationService,
        ReferencesService
        
        // providers used to create fake backend
        //fakeBackendProvider,
        //MockBackend,
        //BaseRequestOptions
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
