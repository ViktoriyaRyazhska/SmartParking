import {Favorite} from "../favorite";
import {Provider} from "../providers/provider";

export class Client {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    favoriteDto: Favorite[];
    providerDto: Provider;
}