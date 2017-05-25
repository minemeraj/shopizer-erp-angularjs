import { Customer } from '../../shared/objects/customer';
import { OrderTotal } from '../../shared/objects/orderTotal';
import { OrderComment } from '../../shared/objects/orderComment';
import { OrderStatusHistory } from '../../shared/objects/orderStatusHistory';

export class Order {
    id : string;
    orderNumber : string;
    estimated : string;
    created : string;
    modified : string;
    creator : string;
    lastUpdator : string;
    customer : Customer;
    orderTotals : OrderTotal[];
    comments : OrderComment[];
    orderStatusHistory : OrderStatusHistory[];
    description : string;
    total : string;
    status : string;
    channel : string;
}