import {Address} from './address';

export class Parking {
    id: number;
    address: Address;
    latitude: number;
    longitude: number;
    token: string;
    price: number;
    providerId: number;
    providerName: string;
    favoritesCount: number;
    spotsCount: number;
  }