import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CarserviceappTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MemDetailComponent } from '../../../../../../main/webapp/app/entities/mem/mem-detail.component';
import { MemService } from '../../../../../../main/webapp/app/entities/mem/mem.service';
import { Mem } from '../../../../../../main/webapp/app/entities/mem/mem.model';

describe('Component Tests', () => {

    describe('Mem Management Detail Component', () => {
        let comp: MemDetailComponent;
        let fixture: ComponentFixture<MemDetailComponent>;
        let service: MemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CarserviceappTestModule],
                declarations: [MemDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MemService,
                    EventManager
                ]
            }).overrideComponent(MemDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Mem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mem).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
