import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BachelorDegreeComponent } from '../list/bachelor-degree.component';
import { BachelorDegreeDetailComponent } from '../detail/bachelor-degree-detail.component';
import { BachelorDegreeUpdateComponent } from '../update/bachelor-degree-update.component';
import { BachelorDegreeRoutingResolveService } from './bachelor-degree-routing-resolve.service';

const bachelorDegreeRoute: Routes = [
  {
    path: '',
    component: BachelorDegreeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BachelorDegreeDetailComponent,
    resolve: {
      bachelorDegree: BachelorDegreeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BachelorDegreeUpdateComponent,
    resolve: {
      bachelorDegree: BachelorDegreeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BachelorDegreeUpdateComponent,
    resolve: {
      bachelorDegree: BachelorDegreeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bachelorDegreeRoute)],
  exports: [RouterModule],
})
export class BachelorDegreeRoutingModule {}
