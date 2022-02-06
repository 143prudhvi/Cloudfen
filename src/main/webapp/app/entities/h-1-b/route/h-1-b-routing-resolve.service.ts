import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IH1B, H1B } from '../h-1-b.model';
import { H1BService } from '../service/h-1-b.service';

@Injectable({ providedIn: 'root' })
export class H1BRoutingResolveService implements Resolve<IH1B> {
  constructor(protected service: H1BService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IH1B> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((h1B: HttpResponse<H1B>) => {
          if (h1B.body) {
            return of(h1B.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new H1B());
  }
}
