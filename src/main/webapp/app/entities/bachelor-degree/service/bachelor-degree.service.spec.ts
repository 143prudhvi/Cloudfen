import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBachelorDegree, BachelorDegree } from '../bachelor-degree.model';

import { BachelorDegreeService } from './bachelor-degree.service';

describe('BachelorDegree Service', () => {
  let service: BachelorDegreeService;
  let httpMock: HttpTestingController;
  let elemDefault: IBachelorDegree;
  let expectedResult: IBachelorDegree | IBachelorDegree[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BachelorDegreeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      course: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BachelorDegree', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BachelorDegree()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BachelorDegree', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          course: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BachelorDegree', () => {
      const patchObject = Object.assign(
        {
          course: 'BBBBBB',
        },
        new BachelorDegree()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BachelorDegree', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          course: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BachelorDegree', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBachelorDegreeToCollectionIfMissing', () => {
      it('should add a BachelorDegree to an empty array', () => {
        const bachelorDegree: IBachelorDegree = { id: 123 };
        expectedResult = service.addBachelorDegreeToCollectionIfMissing([], bachelorDegree);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bachelorDegree);
      });

      it('should not add a BachelorDegree to an array that contains it', () => {
        const bachelorDegree: IBachelorDegree = { id: 123 };
        const bachelorDegreeCollection: IBachelorDegree[] = [
          {
            ...bachelorDegree,
          },
          { id: 456 },
        ];
        expectedResult = service.addBachelorDegreeToCollectionIfMissing(bachelorDegreeCollection, bachelorDegree);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BachelorDegree to an array that doesn't contain it", () => {
        const bachelorDegree: IBachelorDegree = { id: 123 };
        const bachelorDegreeCollection: IBachelorDegree[] = [{ id: 456 }];
        expectedResult = service.addBachelorDegreeToCollectionIfMissing(bachelorDegreeCollection, bachelorDegree);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bachelorDegree);
      });

      it('should add only unique BachelorDegree to an array', () => {
        const bachelorDegreeArray: IBachelorDegree[] = [{ id: 123 }, { id: 456 }, { id: 23832 }];
        const bachelorDegreeCollection: IBachelorDegree[] = [{ id: 123 }];
        expectedResult = service.addBachelorDegreeToCollectionIfMissing(bachelorDegreeCollection, ...bachelorDegreeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bachelorDegree: IBachelorDegree = { id: 123 };
        const bachelorDegree2: IBachelorDegree = { id: 456 };
        expectedResult = service.addBachelorDegreeToCollectionIfMissing([], bachelorDegree, bachelorDegree2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bachelorDegree);
        expect(expectedResult).toContain(bachelorDegree2);
      });

      it('should accept null and undefined values', () => {
        const bachelorDegree: IBachelorDegree = { id: 123 };
        expectedResult = service.addBachelorDegreeToCollectionIfMissing([], null, bachelorDegree, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bachelorDegree);
      });

      it('should return initial array if no BachelorDegree is added', () => {
        const bachelorDegreeCollection: IBachelorDegree[] = [{ id: 123 }];
        expectedResult = service.addBachelorDegreeToCollectionIfMissing(bachelorDegreeCollection, undefined, null);
        expect(expectedResult).toEqual(bachelorDegreeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
