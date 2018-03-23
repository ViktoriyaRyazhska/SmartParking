import {Provider} from './provider';
import {Favorite} from "./favorite";

export class Client {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    favoritesNames: string[];
    provider: string;
    providersId: number;
    role: string;
}