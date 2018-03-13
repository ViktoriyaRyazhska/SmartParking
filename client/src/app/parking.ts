import {Address} from './address';
import {Provider} from './provider';
import {Favorite} from './favorite';
import {Spot} from './spot';

export class Parking {
    id: number;
    addressDto: Address;
    latitude: number;
    longitude: number;
    price: number;
    providerDto: Provider
    favoritesDto: Favorite[];
    spotsDto: Spot[];
    numberSpots: number;
    numberAvailableSpots: number;


}