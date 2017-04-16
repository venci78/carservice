import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Mem } from './mem.model';
import { MemPopupService } from './mem-popup.service';
import { MemService } from './mem.service';

@Component({
    selector: 'jhi-mem-delete-dialog',
    templateUrl: './mem-delete-dialog.component.html'
})
export class MemDeleteDialogComponent {

    mem: Mem;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private memService: MemService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['mem']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.memService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'memListModification',
                content: 'Deleted an mem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mem-delete-popup',
    template: ''
})
export class MemDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private memPopupService: MemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.memPopupService
                .open(MemDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
