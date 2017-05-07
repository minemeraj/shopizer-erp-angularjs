//https://www.genuitec.com/connecting-angular-2-app-java-ee-backend/
import { Description } from '../shared/objects/description';

export interface Brand {
  code : string,
  id : string,
  order : number,
  descriptions : Array<Description>
}