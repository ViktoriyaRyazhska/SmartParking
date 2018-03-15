import {Pipe, PipeTransform} from '@angular/core';
import {Client} from "../client";

@Pipe({
    name: 'filter'
})
export class FilterPipe implements PipeTransform {
    transform(items: Client[], searchText: string): any[] {
        if (!items) return [];
        if (!searchText) return items;
        searchText = searchText.toLowerCase();
        return items.filter(it => {
            return it.lastName.toLocaleLowerCase().includes(searchText) || it.firstName.toLocaleLowerCase().includes(searchText) || it.email.toLocaleLowerCase().includes(searchText);
        });
    }
}