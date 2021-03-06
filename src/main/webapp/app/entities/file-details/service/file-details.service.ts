import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFileDetails, getFileDetailsIdentifier } from '../file-details.model';

export type EntityResponseType = HttpResponse<IFileDetails>;
export type EntityArrayResponseType = HttpResponse<IFileDetails[]>;

@Injectable({ providedIn: 'root' })
export class FileDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/file-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fileDetails: IFileDetails): Observable<EntityResponseType> {
    return this.http.post<IFileDetails>(this.resourceUrl, fileDetails, { observe: 'response' });
  }

  update(fileDetails: IFileDetails): Observable<EntityResponseType> {
    return this.http.put<IFileDetails>(`${this.resourceUrl}/${getFileDetailsIdentifier(fileDetails) as number}`, fileDetails, {
      observe: 'response',
    });
  }

  partialUpdate(fileDetails: IFileDetails): Observable<EntityResponseType> {
    return this.http.patch<IFileDetails>(`${this.resourceUrl}/${getFileDetailsIdentifier(fileDetails) as number}`, fileDetails, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFileDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFileDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFileDetailsToCollectionIfMissing(
    fileDetailsCollection: IFileDetails[],
    ...fileDetailsToCheck: (IFileDetails | null | undefined)[]
  ): IFileDetails[] {
    const fileDetails: IFileDetails[] = fileDetailsToCheck.filter(isPresent);
    if (fileDetails.length > 0) {
      const fileDetailsCollectionIdentifiers = fileDetailsCollection.map(fileDetailsItem => getFileDetailsIdentifier(fileDetailsItem)!);
      const fileDetailsToAdd = fileDetails.filter(fileDetailsItem => {
        const fileDetailsIdentifier = getFileDetailsIdentifier(fileDetailsItem);
        if (fileDetailsIdentifier == null || fileDetailsCollectionIdentifiers.includes(fileDetailsIdentifier)) {
          return false;
        }
        fileDetailsCollectionIdentifiers.push(fileDetailsIdentifier);
        return true;
      });
      return [...fileDetailsToAdd, ...fileDetailsCollection];
    }
    return fileDetailsCollection;
  }
}
