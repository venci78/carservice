import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Work } from './work.model';
import { WorkPopupService } from './work-popup.service';
import { WorkService } from './work.service';
import { Visit, VisitService } from '../visit';

@Component({
    selector: 'jhi-work-dialog',
    templateUrl: './work-dialog.component.html'
})
export class WorkDialogComponent implements OnInit {

    work: Work;
    authorities: any[];
    isSaving: boolean;

    visits: Visit[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private workService: WorkService,
        private visitService: VisitService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['work']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.visitService.query().subscribe(
            (res: Response) => { this.visits = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.work.id !== undefined) {
            this.workService.update(this.work)
                .subscribe((res: Work) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.workService.create(this.work)
                .subscribe((res: Work) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Work) {
        this.eventManager.broadcast({ name: 'workListModification', content: 'OK'});
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

    trackVisitById(index: number, item: Visit) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-work-popup',
    template: ''
})
export class WorkPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workPopupService: WorkPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.workPopupService
                    .open(WorkDialogComponent, params['id']);
            } else {
                this.modalRef = this.workPopupService
                    .open(WorkDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
