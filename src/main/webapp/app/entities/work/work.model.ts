import { Visit } from '../visit';
export class Work {
    constructor(
        public id?: number,
        public work?: string,
        public quantity?: number,
        public priceOne?: number,
        public cost?: number,
        public visit?: Visit,
    ) {
    }
}
