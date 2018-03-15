import {Client} from '../model/view/client';
import {Address} from '../model/view/address';

export class Provider {
    id: number;
    name: string;
    parkingIds: number[];
    employeesDto: Client[];
    address: Address;
    active: boolean;
}