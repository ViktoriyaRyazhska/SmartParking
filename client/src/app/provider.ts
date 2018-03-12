import { Parking } from './parking';
import { Client} from './client';
import { Address } from './address';

export class Provider{
    id: number;
    name: string;
    parkingsDto: Parking[];
    employeesDto: Client[];
    legalAddressDto: Address;
    active: boolean;
}