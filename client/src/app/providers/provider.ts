import { Parking } from '../parking';
import { Client} from '../client';
import { Address } from '../address';

export class Provider{
    id: number;
    name: string;
    parkingIds: number[];
    employeesDto: Client[];
    address: Address;
    active: boolean;
}