import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManagerParkingConfigureComponent} from './manager-parking-configure.component';

describe('ManagerParkingConfigureComponent', () => {
    let component: ManagerParkingConfigureComponent;
    let fixture: ComponentFixture<ManagerParkingConfigureComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ManagerParkingConfigureComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ManagerParkingConfigureComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
