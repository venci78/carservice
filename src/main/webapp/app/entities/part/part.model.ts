import { Visit } from '../visit';
export class Part {
    constructor(
        public id?: number,
        public part?: string,
        public quantity?: number,
        public priceOne?: number,
        public cost?: number,
        public visit?: Visit,
    ) {
    }
}
