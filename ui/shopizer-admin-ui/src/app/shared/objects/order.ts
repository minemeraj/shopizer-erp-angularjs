import { Customer } from '../../shared/objects/customer';
import { OrderTotal } from '../../shared/objects/orderTotal';

export class Order {
    id : string;
    number : number;
    estimated : string;
    created : string;
    creator : string;
    customer : Customer;
    orderTotals : OrderTotal[];
    description : string;
    total : string;
}