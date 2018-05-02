import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../environments/environment';
import {Parking} from "../model/view/parking";

@Injectable()
export class StatisticsService {

    private statisticUrl = environment.apiUrl + '/statistic';

    constructor(private http: HttpClient) {
    }

    getParkingsStreetsByAnyMatching(city: string, street: string): Observable<string[]> {
        return this.http.get<string[]>(this.statisticUrl + '/parkings-streets', {
            params: {
                city: city,
                street: street
            }
        });
    }

    getParkingsCitiesByAnyMatching(input: string): Observable<string[]> {
        return this.http.get<string[]>(this.statisticUrl + '/parkings-cities/' + input);
    }

    getAllParkingsCities(): Observable<string[]> {
        return this.http.get<string[]>(this.statisticUrl + '/all-parkings-cities');
    }

    getBestParkingsByCityStreetDate(city: string, street: string, days: number): Observable<Parking[]> {
        return this.http.get<Parking[]>(this.statisticUrl + '/best-parkings', {
            params: {
                city: city,
                street: street,
                days: days.toString()
            }
        });
    }

    getBestParkingsInTheCityByDate(city: string, days: number): Observable<Parking[]> {
        return this.http.get<Parking[]>(this.statisticUrl + '/best-parkings-in-city', {
            params: {
                city: city,
                days: days.toString()
            }
        });
    }

    getBestParkingsByLocation(latitude: number, longitude: number, radius: number, days: number): Observable<Parking[]> {
        return this.http.get<Parking[]>(this.statisticUrl + '/best-parkings-by-location', {
            params: {
                latitude: latitude.toString(),
                longitude: longitude.toString(),
                radius: radius.toString(),
                days: days.toString()
            }
        });
    }

    getBestParkingsByLocationPriceAndFunctional(latitude: number, longitude: number, radius: number, days: number,
                                                minPrice: number, maxPrice: number, hasCharger: boolean, hasInvalid: boolean,
                                                isCovered: boolean): Observable<Parking[]> {
        return this.http.get<Parking[]>(this.statisticUrl + '/best-parkings-by-location-and-properties', {
            params: {
                latitude: latitude.toString(),
                longitude: longitude.toString(),
                radius: radius.toString(),
                days: days.toString(),
                minPrice: minPrice.toString(),
                maxPrice: maxPrice.toString(),
                hasCharger: hasCharger.toString(),
                hasInvalid: hasInvalid.toString(),
                isCovered: isCovered.toString()
            }
        });
    }

}
