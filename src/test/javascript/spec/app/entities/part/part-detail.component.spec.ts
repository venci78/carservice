import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CarserviceappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PartDetailComponent } from '../../../../../../main/webapp/app/entities/part/part-detail.component';
import { PartService } from '../../../../../../main/webapp/app/entities/part/part.service';
import { Part } from '../../../../../../main/webapp/app/entities/part/part.model';

describe('Component Tests', () => {

    describe('Part Management Detail Component', () => {
        let comp: PartDetailComponent;
        let fixture: ComponentFixture<PartDetailComponent>;
        let service: PartService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CarserviceappTestModule],
                declarations: [PartDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PartService,
                    EventManager
                ]
            }).overrideComponent(PartDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Part(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.part).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
