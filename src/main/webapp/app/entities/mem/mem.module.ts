import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarserviceappSharedModule } from '../../shared';
import {
    MemService,
    MemPopupService,
    MemComponent,
    MemDetailComponent,
    MemDialogComponent,
    MemPopupComponent,
    MemDeletePopupComponent,
    MemDeleteDialogComponent,
    memRoute,
    memPopupRoute,
} from './';

const ENTITY_STATES = [
    ...memRoute,
    ...memPopupRoute,
];

@NgModule({
    imports: [
        CarserviceappSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MemComponent,
        MemDetailComponent,
        MemDialogComponent,
        MemDeleteDialogComponent,
        MemPopupComponent,
        MemDeletePopupComponent,
    ],
    entryComponents: [
        MemComponent,
        MemDialogComponent,
        MemPopupComponent,
        MemDeleteDialogComponent,
        MemDeletePopupComponent,
    ],
    providers: [
        MemService,
        MemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarserviceappMemModule {}
