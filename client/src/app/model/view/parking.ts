import {Address} from './address';
import {Provider} from '../../providers/provider';
import {Favorite} from './favorite';
import {Spot} from './spot';

export class Parking {
    id: number;
    addressDto: Address;
    latitude: number;
    longitude: number;
    price: number;
    providerDto: Provider;
    favoritesDto: Favorite[];
    numberSpots: number;
    numberAvailableSpots: number;
  }