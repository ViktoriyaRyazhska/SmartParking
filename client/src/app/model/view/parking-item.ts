import {Address} from "./address";

export class ParkingItem {
    id: number;
    address: Address;
    latitude: number;
    longitude: number;
    price: number;
    spotsNumber: number;
    availableSpotsNumber: number;
}