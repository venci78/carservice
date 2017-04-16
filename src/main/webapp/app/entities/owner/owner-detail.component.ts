import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Owner } from './owner.model';
import { OwnerService } from './owner.service';

@Component({
    selector: 'jhi-owner-detail',
    templateUrl: './owner-detail.component.html'
})
export class OwnerDetailComponent implements OnInit, OnDestroy {

    owner: Owner;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private ownerService: OwnerService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['owner']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOwners();
    }

    load(id) {
        this.ownerService.find(id).subscribe((owner) => {
            this.owner = owner;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOwners() {
        this.eventSubscriber = this.eventManager.subscribe('ownerListModification', (response) => this.load(this.owner.id));
    }
}
