import { Visit } from '../visit';
export class Worker {
    constructor(
        public id?: number,
        public name?: string,
        public postition?: string,
        public visit?: Visit,
    ) {
    }
}
