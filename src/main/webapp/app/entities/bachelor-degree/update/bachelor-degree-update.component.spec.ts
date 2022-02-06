import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BachelorDegreeService } from '../service/bachelor-degree.service';
import { IBachelorDegree, BachelorDegree } from '../bachelor-degree.model';

import { BachelorDegreeUpdateComponent } from './bachelor-degree-update.component';

describe('BachelorDegree Management Update Component', () => {
  let comp: BachelorDegreeUpdateComponent;
  let fixture: ComponentFixture<BachelorDegreeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bachelorDegreeService: BachelorDegreeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BachelorDegreeUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BachelorDegreeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BachelorDegreeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bachelorDegreeService = TestBed.inject(BachelorDegreeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bachelorDegree: IBachelorDegree = { id: 456 };

      activatedRoute.data = of({ bachelorDegree });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bachelorDegree));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BachelorDegree>>();
      const bachelorDegree = { id: 123 };
      jest.spyOn(bachelorDegreeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bachelorDegree });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bachelorDegree }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bachelorDegreeService.update).toHaveBeenCalledWith(bachelorDegree);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BachelorDegree>>();
      const bachelorDegree = new BachelorDegree();
      jest.spyOn(bachelorDegreeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bachelorDegree });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bachelorDegree }));
      saveSubject.complete();

      // THEN
      expect(bachelorDegreeService.create).toHaveBeenCalledWith(bachelorDegree);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BachelorDegree>>();
      const bachelorDegree = { id: 123 };
      jest.spyOn(bachelorDegreeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bachelorDegree });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bachelorDegreeService.update).toHaveBeenCalledWith(bachelorDegree);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
