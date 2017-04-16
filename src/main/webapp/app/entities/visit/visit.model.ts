import { Car } from '../car';
import { Worker } from '../worker';
export class Visit {
    constructor(
        public id?: number,
        public km?: number,
        public date?: any,
        public partsCost?: number,
        public workCost?: number,
        public allCost?: number,
        public car?: Car,
        public worker?: Worker,
    ) {
    }
}
