import { Address } from './address';
import { Provider } from './providers/provider';
import { Favorite } from './favorite';

export class Parking {
    id: number;
    addressDto: Address;
    latitude: number;
    longitude: number;
    price: number;
    providerDto: Provider
    favoritesDto: Favorite[];
    numberSpots: number;
    numberAvailableSpots: number;
  }