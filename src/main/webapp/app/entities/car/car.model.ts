import { Visit } from '../visit';
import { Owner } from '../owner';
export class Car {
    constructor(
        public id?: number,
        public reg?: string,
        public brand?: string,
        public model?: string,
        public comment?: string,
        public visit?: Visit,
        public owner?: Owner,
    ) {
    }
}
