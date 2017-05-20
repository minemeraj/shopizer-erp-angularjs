import { Route } from '@angular/router';

import { HomeRoutes } from './home/home.routes';
import { ChartRoutes } from './charts/chart.route';
import { BlankPageRoutes } from './blank-page/blankPage.routes';
import { TableRoutes } from './tables/table.routes';
import { FormRoutes } from './forms/forms.routes';
import { GridRoutes } from './grid/grid.routes';
import { CustomerListRoutes } from './customer-list/customer-list.routes';
import { CustomerRoutes } from './customer/customer.routes';
import { OrderRoutes } from './order/order.routes';
import { UserRoutes } from './user/user.routes';
import { UserListRoutes } from './user-list/user-list.routes';
import { OrderListRoutes } from './order-list/order-list.routes';
import { PasswordRoutes } from './password/password.routes';
import { BSComponentRoutes } from './bs-component/bsComponent.routes';
import { BSElementRoutes } from './bs-element/bsElement.routes';

import { AuthGuard } from '../_guard/index';

import { DashboardComponent } from './index';

export const DashboardRoutes: Route[] = [
    {
      path: 'dashboard',
      component: DashboardComponent,
      canActivate: [AuthGuard],
      children: [
        ...HomeRoutes,
        ...ChartRoutes,
        ...BSComponentRoutes,
        ...TableRoutes,
        ...BlankPageRoutes,
        ...FormRoutes,
        ...GridRoutes,
        ...BSElementRoutes,
        ...CustomerListRoutes,
        ...CustomerRoutes,
        ...OrderRoutes,
        ...OrderListRoutes,
        ...UserRoutes,
        ...UserListRoutes,
        ...PasswordRoutes
      ]
    }
];
