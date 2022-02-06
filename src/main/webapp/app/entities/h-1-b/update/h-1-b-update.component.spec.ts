import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { H1BService } from '../service/h-1-b.service';
import { IH1B, H1B } from '../h-1-b.model';

import { H1BUpdateComponent } from './h-1-b-update.component';

describe('H1B Management Update Component', () => {
  let comp: H1BUpdateComponent;
  let fixture: ComponentFixture<H1BUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let h1BService: H1BService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [H1BUpdateComponent],
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
      .overrideTemplate(H1BUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(H1BUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    h1BService = TestBed.inject(H1BService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const h1B: IH1B = { id: 456 };

      activatedRoute.data = of({ h1B });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(h1B));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<H1B>>();
      const h1B = { id: 123 };
      jest.spyOn(h1BService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ h1B });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: h1B }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(h1BService.update).toHaveBeenCalledWith(h1B);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<H1B>>();
      const h1B = new H1B();
      jest.spyOn(h1BService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ h1B });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: h1B }));
      saveSubject.complete();

      // THEN
      expect(h1BService.create).toHaveBeenCalledWith(h1B);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<H1B>>();
      const h1B = { id: 123 };
      jest.spyOn(h1BService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ h1B });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(h1BService.update).toHaveBeenCalledWith(h1B);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
