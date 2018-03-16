import {Provider} from "../../providers/provider";
import {Favorite} from "./favorite";

export class Client{
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    favorite: Favorite[];
    provider: Provider;
    role: string;
}