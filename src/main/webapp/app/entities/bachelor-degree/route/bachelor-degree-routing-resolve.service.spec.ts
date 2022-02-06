import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBachelorDegree, BachelorDegree } from '../bachelor-degree.model';
import { BachelorDegreeService } from '../service/bachelor-degree.service';

import { BachelorDegreeRoutingResolveService } from './bachelor-degree-routing-resolve.service';

describe('BachelorDegree routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BachelorDegreeRoutingResolveService;
  let service: BachelorDegreeService;
  let resultBachelorDegree: IBachelorDegree | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(BachelorDegreeRoutingResolveService);
    service = TestBed.inject(BachelorDegreeService);
    resultBachelorDegree = undefined;
  });

  describe('resolve', () => {
    it('should return IBachelorDegree returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBachelorDegree = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBachelorDegree).toEqual({ id: 123 });
    });

    it('should return new IBachelorDegree if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBachelorDegree = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBachelorDegree).toEqual(new BachelorDegree());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BachelorDegree })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBachelorDegree = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBachelorDegree).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
