import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarserviceappSharedModule } from '../../shared';
import {
    VisitService,
    VisitPopupService,
    VisitComponent,
    VisitDetailComponent,
    VisitDialogComponent,
    VisitPopupComponent,
    VisitDeletePopupComponent,
    VisitDeleteDialogComponent,
    visitRoute,
    visitPopupRoute,
} from './';

const ENTITY_STATES = [
    ...visitRoute,
    ...visitPopupRoute,
];

@NgModule({
    imports: [
        CarserviceappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VisitComponent,
        VisitDetailComponent,
        VisitDialogComponent,
        VisitDeleteDialogComponent,
        VisitPopupComponent,
        VisitDeletePopupComponent,
    ],
    entryComponents: [
        VisitComponent,
        VisitDialogComponent,
        VisitPopupComponent,
        VisitDeleteDialogComponent,
        VisitDeletePopupComponent,
    ],
    providers: [
        VisitService,
        VisitPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarserviceappVisitModule {}
