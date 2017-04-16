import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { WorkComponent } from './work.component';
import { WorkDetailComponent } from './work-detail.component';
import { WorkPopupComponent } from './work-dialog.component';
import { WorkDeletePopupComponent } from './work-delete-dialog.component';

import { Principal } from '../../shared';

export const workRoute: Routes = [
  {
    path: 'work',
    component: WorkComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.work.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'work/:id',
    component: WorkDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.work.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const workPopupRoute: Routes = [
  {
    path: 'work-new',
    component: WorkPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.work.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'work/:id/edit',
    component: WorkPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.work.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'work/:id/delete',
    component: WorkDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.work.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
