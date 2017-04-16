import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CarserviceappOwnerModule } from './owner/owner.module';
import { CarserviceappCarModule } from './car/car.module';
import { CarserviceappMemModule } from './mem/mem.module';
import { CarserviceappVisitModule } from './visit/visit.module';
import { CarserviceappPartModule } from './part/part.module';
import { CarserviceappWorkModule } from './work/work.module';
import { CarserviceappWorkerModule } from './worker/worker.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CarserviceappOwnerModule,
        CarserviceappCarModule,
        CarserviceappMemModule,
        CarserviceappVisitModule,
        CarserviceappPartModule,
        CarserviceappWorkModule,
        CarserviceappWorkerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarserviceappEntityModule {}
