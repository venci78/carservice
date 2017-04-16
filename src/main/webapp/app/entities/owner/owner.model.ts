import { Car } from '../car';
export class Owner {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public tel?: string,
        public car?: Car,
    ) {
    }
}
