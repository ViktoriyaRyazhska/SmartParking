import {Address} from './address';

export class Provider {
    id: number;
    name: string;
    parkingIds: number[];
    address: Address;
    active: boolean;
}