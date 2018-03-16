import {Provider} from "../../providers/provider";
import {Favorite} from "./favorite";

export class Client{
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    favoritesId: number[];
    favoritesNames: string[];
    provider: Provider;
    role: string;
}