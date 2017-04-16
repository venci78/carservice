import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Part } from './part.model';
import { PartPopupService } from './part-popup.service';
import { PartService } from './part.service';

@Component({
    selector: 'jhi-part-delete-dialog',
    templateUrl: './part-delete-dialog.component.html'
})
export class PartDeleteDialogComponent {

    part: Part;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private partService: PartService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['part']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'partListModification',
                content: 'Deleted an part'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-part-delete-popup',
    template: ''
})
export class PartDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partPopupService: PartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.partPopupService
                .open(PartDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
