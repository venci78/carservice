import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CarserviceappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OwnerDetailComponent } from '../../../../../../main/webapp/app/entities/owner/owner-detail.component';
import { OwnerService } from '../../../../../../main/webapp/app/entities/owner/owner.service';
import { Owner } from '../../../../../../main/webapp/app/entities/owner/owner.model';

describe('Component Tests', () => {

    describe('Owner Management Detail Component', () => {
        let comp: OwnerDetailComponent;
        let fixture: ComponentFixture<OwnerDetailComponent>;
        let service: OwnerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CarserviceappTestModule],
                declarations: [OwnerDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OwnerService,
                    EventManager
                ]
            }).overrideComponent(OwnerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OwnerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OwnerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Owner(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.owner).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
