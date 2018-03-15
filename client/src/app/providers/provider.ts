import { Parking } from '../parking';
import { Address } from '../address';
import {Client} from "../clients/client";

export class Provider{
    id: number;
    name: string;
    parkingIds: number[];
    employeesDto: Client[];
    address: Address;
    active: boolean;
}