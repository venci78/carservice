import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Part } from './part.model';
import { PartPopupService } from './part-popup.service';
import { PartService } from './part.service';
import { Visit, VisitService } from '../visit';

@Component({
    selector: 'jhi-part-dialog',
    templateUrl: './part-dialog.component.html'
})
export class PartDialogComponent implements OnInit {

    part: Part;
    authorities: any[];
    isSaving: boolean;

    visits: Visit[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private partService: PartService,
        private visitService: VisitService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['part']);
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
        if (this.part.id !== undefined) {
            this.partService.update(this.part)
                .subscribe((res: Part) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.partService.create(this.part)
                .subscribe((res: Part) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Part) {
        this.eventManager.broadcast({ name: 'partListModification', content: 'OK'});
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
    selector: 'jhi-part-popup',
    template: ''
})
export class PartPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partPopupService: PartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.partPopupService
                    .open(PartDialogComponent, params['id']);
            } else {
                this.modalRef = this.partPopupService
                    .open(PartDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
