import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarserviceappSharedModule } from '../../shared';
import {
    PartService,
    PartPopupService,
    PartComponent,
    PartDetailComponent,
    PartDialogComponent,
    PartPopupComponent,
    PartDeletePopupComponent,
    PartDeleteDialogComponent,
    partRoute,
    partPopupRoute,
} from './';

const ENTITY_STATES = [
    ...partRoute,
    ...partPopupRoute,
];

@NgModule({
    imports: [
        CarserviceappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PartComponent,
        PartDetailComponent,
        PartDialogComponent,
        PartDeleteDialogComponent,
        PartPopupComponent,
        PartDeletePopupComponent,
    ],
    entryComponents: [
        PartComponent,
        PartDialogComponent,
        PartPopupComponent,
        PartDeleteDialogComponent,
        PartDeletePopupComponent,
    ],
    providers: [
        PartService,
        PartPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarserviceappPartModule {}
