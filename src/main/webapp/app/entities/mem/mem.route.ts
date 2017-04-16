import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MemComponent } from './mem.component';
import { MemDetailComponent } from './mem-detail.component';
import { MemPopupComponent } from './mem-dialog.component';
import { MemDeletePopupComponent } from './mem-delete-dialog.component';

import { Principal } from '../../shared';

export const memRoute: Routes = [
  {
    path: 'mem',
    component: MemComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.mem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'mem/:id',
    component: MemDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.mem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const memPopupRoute: Routes = [
  {
    path: 'mem-new',
    component: MemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.mem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'mem/:id/edit',
    component: MemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.mem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'mem/:id/delete',
    component: MemDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.mem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
