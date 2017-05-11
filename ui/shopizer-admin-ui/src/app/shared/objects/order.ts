import { Customer } from '../../shared/objects/customer';
import { OrderTotal } from '../../shared/objects/orderTotal';
import { OrderComment } from '../../shared/objects/orderComment';

export class Order {
    id : string;
    number : number;
    estimated : string;
    created : string;
    creator : string;
    customer : Customer;
    orderTotals : OrderTotal[];
    comments : OrderComment[];
    description : string;
    total : string;
}