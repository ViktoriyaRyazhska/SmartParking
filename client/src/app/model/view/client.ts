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
    favorite: Favorite[];
    provider: string;
    role: string;
}