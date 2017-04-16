import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PartComponent } from './part.component';
import { PartDetailComponent } from './part-detail.component';
import { PartPopupComponent } from './part-dialog.component';
import { PartDeletePopupComponent } from './part-delete-dialog.component';

import { Principal } from '../../shared';

export const partRoute: Routes = [
  {
    path: 'part',
    component: PartComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.part.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'part/:id',
    component: PartDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.part.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const partPopupRoute: Routes = [
  {
    path: 'part-new',
    component: PartPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.part.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'part/:id/edit',
    component: PartPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.part.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'part/:id/delete',
    component: PartDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'carserviceappApp.part.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
