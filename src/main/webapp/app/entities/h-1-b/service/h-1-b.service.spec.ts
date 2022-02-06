import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { GenderType } from 'app/entities/enumerations/gender-type.model';
import { CategoryType } from 'app/entities/enumerations/category-type.model';
import { IH1B, H1B } from '../h-1-b.model';

import { H1BService } from './h-1-b.service';

describe('H1B Service', () => {
  let service: H1BService;
  let httpMock: HttpTestingController;
  let elemDefault: IH1B;
  let expectedResult: IH1B | IH1B[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(H1BService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      userId: 'AAAAAAA',
      firstName: 'AAAAAAA',
      middleName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      dateOfBirth: currentDate,
      countryOfBirth: 'AAAAAAA',
      countryOfCitizenShip: 'AAAAAAA',
      passportNumber: 'AAAAAAA',
      gender: GenderType.MALE,
      category: CategoryType.REGULARCAP,
      email: 'AAAAAAA',
      currentAddress: 'AAAAAAA',
      phoneNumber: 'AAAAAAA',
      bachelorDegree: 'AAAAAAA',
      year: 'AAAAAAA',
      currentVisaStatus: 'AAAAAAA',
      referedBy: 'AAAAAAA',
      passportFrontPage: 'AAAAAAA',
      passportBackPage: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a H1B', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.create(new H1B()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a H1B', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          userId: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
          countryOfBirth: 'BBBBBB',
          countryOfCitizenShip: 'BBBBBB',
          passportNumber: 'BBBBBB',
          gender: 'BBBBBB',
          category: 'BBBBBB',
          email: 'BBBBBB',
          currentAddress: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          bachelorDegree: 'BBBBBB',
          year: 'BBBBBB',
          currentVisaStatus: 'BBBBBB',
          referedBy: 'BBBBBB',
          passportFrontPage: 'BBBBBB',
          passportBackPage: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a H1B', () => {
      const patchObject = Object.assign(
        {
          userId: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          countryOfCitizenShip: 'BBBBBB',
          passportNumber: 'BBBBBB',
          gender: 'BBBBBB',
          category: 'BBBBBB',
          email: 'BBBBBB',
          currentVisaStatus: 'BBBBBB',
          passportFrontPage: 'BBBBBB',
          passportBackPage: 'BBBBBB',
        },
        new H1B()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of H1B', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          userId: 'BBBBBB',
          firstName: 'BBBBBB',
          middleName: 'BBBBBB',
          lastName: 'BBBBBB',
          dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
          countryOfBirth: 'BBBBBB',
          countryOfCitizenShip: 'BBBBBB',
          passportNumber: 'BBBBBB',
          gender: 'BBBBBB',
          category: 'BBBBBB',
          email: 'BBBBBB',
          currentAddress: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          bachelorDegree: 'BBBBBB',
          year: 'BBBBBB',
          currentVisaStatus: 'BBBBBB',
          referedBy: 'BBBBBB',
          passportFrontPage: 'BBBBBB',
          passportBackPage: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfBirth: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a H1B', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addH1BToCollectionIfMissing', () => {
      it('should add a H1B to an empty array', () => {
        const h1B: IH1B = { id: 123 };
        expectedResult = service.addH1BToCollectionIfMissing([], h1B);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(h1B);
      });

      it('should not add a H1B to an array that contains it', () => {
        const h1B: IH1B = { id: 123 };
        const h1BCollection: IH1B[] = [
          {
            ...h1B,
          },
          { id: 456 },
        ];
        expectedResult = service.addH1BToCollectionIfMissing(h1BCollection, h1B);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a H1B to an array that doesn't contain it", () => {
        const h1B: IH1B = { id: 123 };
        const h1BCollection: IH1B[] = [{ id: 456 }];
        expectedResult = service.addH1BToCollectionIfMissing(h1BCollection, h1B);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(h1B);
      });

      it('should add only unique H1B to an array', () => {
        const h1BArray: IH1B[] = [{ id: 123 }, { id: 456 }, { id: 6080 }];
        const h1BCollection: IH1B[] = [{ id: 123 }];
        expectedResult = service.addH1BToCollectionIfMissing(h1BCollection, ...h1BArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const h1B: IH1B = { id: 123 };
        const h1B2: IH1B = { id: 456 };
        expectedResult = service.addH1BToCollectionIfMissing([], h1B, h1B2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(h1B);
        expect(expectedResult).toContain(h1B2);
      });

      it('should accept null and undefined values', () => {
        const h1B: IH1B = { id: 123 };
        expectedResult = service.addH1BToCollectionIfMissing([], null, h1B, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(h1B);
      });

      it('should return initial array if no H1B is added', () => {
        const h1BCollection: IH1B[] = [{ id: 123 }];
        expectedResult = service.addH1BToCollectionIfMissing(h1BCollection, undefined, null);
        expect(expectedResult).toEqual(h1BCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
