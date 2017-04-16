import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Mem } from './mem.model';
import { MemService } from './mem.service';
@Injectable()
export class MemPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private memService: MemService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.memService.find(id).subscribe((mem) => {
                mem.date = this.datePipe
                    .transform(mem.date, 'yyyy-MM-ddThh:mm');
                mem.afterDate = this.datePipe
                    .transform(mem.afterDate, 'yyyy-MM-ddThh:mm');
                this.memModalRef(component, mem);
            });
        } else {
            return this.memModalRef(component, new Mem());
        }
    }

    memModalRef(component: Component, mem: Mem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mem = mem;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
