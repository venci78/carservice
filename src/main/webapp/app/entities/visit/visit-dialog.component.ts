import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Visit } from './visit.model';
import { VisitPopupService } from './visit-popup.service';
import { VisitService } from './visit.service';
import { Car, CarService } from '../car';
import { Worker, WorkerService } from '../worker';

@Component({
    selector: 'jhi-visit-dialog',
    templateUrl: './visit-dialog.component.html'
})
export class VisitDialogComponent implements OnInit {

    visit: Visit;
    authorities: any[];
    isSaving: boolean;

    cars: Car[];

    workers: Worker[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private visitService: VisitService,
        private carService: CarService,
        private workerService: WorkerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['visit']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.carService.query().subscribe(
            (res: Response) => { this.cars = res.json(); }, (res: Response) => this.onError(res.json()));
        this.workerService.query().subscribe(
            (res: Response) => { this.workers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.visit.id !== undefined) {
            this.visitService.update(this.visit)
                .subscribe((res: Visit) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.visitService.create(this.visit)
                .subscribe((res: Visit) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Visit) {
        this.eventManager.broadcast({ name: 'visitListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCarById(index: number, item: Car) {
        return item.id;
    }

    trackWorkerById(index: number, item: Worker) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-visit-popup',
    template: ''
})
export class VisitPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visitPopupService: VisitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.visitPopupService
                    .open(VisitDialogComponent, params['id']);
            } else {
                this.modalRef = this.visitPopupService
                    .open(VisitDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
