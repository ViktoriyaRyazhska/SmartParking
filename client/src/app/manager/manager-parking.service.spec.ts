import {inject, TestBed} from '@angular/core/testing';

import {ManagerParkingService} from './manager-parking.service';

describe('ManagerParkingService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [ManagerParkingService]
        });
    });

    it('should be created', inject([ManagerParkingService], (service: ManagerParkingService) => {
        expect(service).toBeTruthy();
    }));
});
