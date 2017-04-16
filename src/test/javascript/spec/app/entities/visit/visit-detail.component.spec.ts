import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CarserviceappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VisitDetailComponent } from '../../../../../../main/webapp/app/entities/visit/visit-detail.component';
import { VisitService } from '../../../../../../main/webapp/app/entities/visit/visit.service';
import { Visit } from '../../../../../../main/webapp/app/entities/visit/visit.model';

describe('Component Tests', () => {

    describe('Visit Management Detail Component', () => {
        let comp: VisitDetailComponent;
        let fixture: ComponentFixture<VisitDetailComponent>;
        let service: VisitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CarserviceappTestModule],
                declarations: [VisitDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VisitService,
                    EventManager
                ]
            }).overrideComponent(VisitDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VisitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisitService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Visit(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.visit).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
