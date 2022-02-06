import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBachelorDegree, BachelorDegree } from '../bachelor-degree.model';
import { BachelorDegreeService } from '../service/bachelor-degree.service';

@Injectable({ providedIn: 'root' })
export class BachelorDegreeRoutingResolveService implements Resolve<IBachelorDegree> {
  constructor(protected service: BachelorDegreeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBachelorDegree> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bachelorDegree: HttpResponse<BachelorDegree>) => {
          if (bachelorDegree.body) {
            return of(bachelorDegree.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BachelorDegree());
  }
}
