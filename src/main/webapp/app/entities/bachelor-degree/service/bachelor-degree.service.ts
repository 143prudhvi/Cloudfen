import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBachelorDegree, getBachelorDegreeIdentifier } from '../bachelor-degree.model';

export type EntityResponseType = HttpResponse<IBachelorDegree>;
export type EntityArrayResponseType = HttpResponse<IBachelorDegree[]>;

@Injectable({ providedIn: 'root' })
export class BachelorDegreeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bachelor-degrees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bachelorDegree: IBachelorDegree): Observable<EntityResponseType> {
    return this.http.post<IBachelorDegree>(this.resourceUrl, bachelorDegree, { observe: 'response' });
  }

  update(bachelorDegree: IBachelorDegree): Observable<EntityResponseType> {
    return this.http.put<IBachelorDegree>(`${this.resourceUrl}/${getBachelorDegreeIdentifier(bachelorDegree) as number}`, bachelorDegree, {
      observe: 'response',
    });
  }

  partialUpdate(bachelorDegree: IBachelorDegree): Observable<EntityResponseType> {
    return this.http.patch<IBachelorDegree>(
      `${this.resourceUrl}/${getBachelorDegreeIdentifier(bachelorDegree) as number}`,
      bachelorDegree,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBachelorDegree>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBachelorDegree[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBachelorDegreeToCollectionIfMissing(
    bachelorDegreeCollection: IBachelorDegree[],
    ...bachelorDegreesToCheck: (IBachelorDegree | null | undefined)[]
  ): IBachelorDegree[] {
    const bachelorDegrees: IBachelorDegree[] = bachelorDegreesToCheck.filter(isPresent);
    if (bachelorDegrees.length > 0) {
      const bachelorDegreeCollectionIdentifiers = bachelorDegreeCollection.map(
        bachelorDegreeItem => getBachelorDegreeIdentifier(bachelorDegreeItem)!
      );
      const bachelorDegreesToAdd = bachelorDegrees.filter(bachelorDegreeItem => {
        const bachelorDegreeIdentifier = getBachelorDegreeIdentifier(bachelorDegreeItem);
        if (bachelorDegreeIdentifier == null || bachelorDegreeCollectionIdentifiers.includes(bachelorDegreeIdentifier)) {
          return false;
        }
        bachelorDegreeCollectionIdentifiers.push(bachelorDegreeIdentifier);
        return true;
      });
      return [...bachelorDegreesToAdd, ...bachelorDegreeCollection];
    }
    return bachelorDegreeCollection;
  }
}
