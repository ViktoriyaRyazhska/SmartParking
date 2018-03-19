import {Address} from "../model/view/address";

export class ManagerParkingRequest {
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