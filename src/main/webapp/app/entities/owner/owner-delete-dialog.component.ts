import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Owner } from './owner.model';
import { OwnerPopupService } from './owner-popup.service';
import { OwnerService } from './owner.service';

@Component({
    selector: 'jhi-owner-delete-dialog',
    templateUrl: './owner-delete-dialog.component.html'
})
export class OwnerDeleteDialogComponent {

    owner: Owner;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private ownerService: OwnerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['owner']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ownerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ownerListModification',
                content: 'Deleted an owner'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-owner-delete-popup',
    template: ''
})
export class OwnerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ownerPopupService: OwnerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.ownerPopupService
                .open(OwnerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
