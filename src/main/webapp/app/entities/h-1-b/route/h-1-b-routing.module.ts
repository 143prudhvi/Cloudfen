import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { H1BComponent } from '../list/h-1-b.component';
import { H1BDetailComponent } from '../detail/h-1-b-detail.component';
import { H1BUpdateComponent } from '../update/h-1-b-update.component';
import { H1BRoutingResolveService } from './h-1-b-routing-resolve.service';

const h1BRoute: Routes = [
  {
    path: '',
    component: H1BComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: H1BDetailComponent,
    resolve: {
      h1B: H1BRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: H1BUpdateComponent,
    resolve: {
      h1B: H1BRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: H1BUpdateComponent,
    resolve: {
      h1B: H1BRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(h1BRoute)],
  exports: [RouterModule],
})
export class H1BRoutingModule {}
