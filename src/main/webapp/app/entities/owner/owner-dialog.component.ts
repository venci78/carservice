import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Owner } from './owner.model';
import { OwnerPopupService } from './owner-popup.service';
import { OwnerService } from './owner.service';

@Component({
    selector: 'jhi-owner-dialog',
    templateUrl: './owner-dialog.component.html'
})
export class OwnerDialogComponent implements OnInit {

    owner: Owner;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private ownerService: OwnerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['owner']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.owner.id !== undefined) {
            this.ownerService.update(this.owner)
                .subscribe((res: Owner) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.ownerService.create(this.owner)
                .subscribe((res: Owner) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Owner) {
        this.eventManager.broadcast({ name: 'ownerListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-owner-popup',
    template: ''
})
export class OwnerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ownerPopupService: OwnerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.ownerPopupService
                    .open(OwnerDialogComponent, params['id']);
            } else {
                this.modalRef = this.ownerPopupService
                    .open(OwnerDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
