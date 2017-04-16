import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Mem } from './mem.model';
import { MemService } from './mem.service';

@Component({
    selector: 'jhi-mem-detail',
    templateUrl: './mem-detail.component.html'
})
export class MemDetailComponent implements OnInit, OnDestroy {

    mem: Mem;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private memService: MemService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['mem']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMems();
    }

    load(id) {
        this.memService.find(id).subscribe((mem) => {
            this.mem = mem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMems() {
        this.eventSubscriber = this.eventManager.subscribe('memListModification', (response) => this.load(this.mem.id));
    }
}
