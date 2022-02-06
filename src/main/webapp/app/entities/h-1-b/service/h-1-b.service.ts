import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IH1B, getH1BIdentifier } from '../h-1-b.model';

export type EntityResponseType = HttpResponse<IH1B>;
export type EntityArrayResponseType = HttpResponse<IH1B[]>;

@Injectable({ providedIn: 'root' })
export class H1BService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/h-1-bs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(h1B: IH1B): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(h1B);
    return this.http
      .post<IH1B>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(h1B: IH1B): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(h1B);
    return this.http
      .put<IH1B>(`${this.resourceUrl}/${getH1BIdentifier(h1B) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(h1B: IH1B): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(h1B);
    return this.http
      .patch<IH1B>(`${this.resourceUrl}/${getH1BIdentifier(h1B) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IH1B>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IH1B[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addH1BToCollectionIfMissing(h1BCollection: IH1B[], ...h1BSToCheck: (IH1B | null | undefined)[]): IH1B[] {
    const h1BS: IH1B[] = h1BSToCheck.filter(isPresent);
    if (h1BS.length > 0) {
      const h1BCollectionIdentifiers = h1BCollection.map(h1BItem => getH1BIdentifier(h1BItem)!);
      const h1BSToAdd = h1BS.filter(h1BItem => {
        const h1BIdentifier = getH1BIdentifier(h1BItem);
        if (h1BIdentifier == null || h1BCollectionIdentifiers.includes(h1BIdentifier)) {
          return false;
        }
        h1BCollectionIdentifiers.push(h1BIdentifier);
        return true;
      });
      return [...h1BSToAdd, ...h1BCollection];
    }
    return h1BCollection;
  }

  protected convertDateFromClient(h1B: IH1B): IH1B {
    return Object.assign({}, h1B, {
      dateOfBirth: h1B.dateOfBirth?.isValid() ? h1B.dateOfBirth.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? dayjs(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((h1B: IH1B) => {
        h1B.dateOfBirth = h1B.dateOfBirth ? dayjs(h1B.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}
