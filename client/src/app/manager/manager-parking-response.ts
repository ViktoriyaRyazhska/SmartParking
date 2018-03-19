import {Address} from "../model/view/address";

export class ManagerParkingResponse {
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