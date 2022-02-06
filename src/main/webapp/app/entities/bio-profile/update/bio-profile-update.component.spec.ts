import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BioProfileService } from '../service/bio-profile.service';
import { IBioProfile, BioProfile } from '../bio-profile.model';

import { BioProfileUpdateComponent } from './bio-profile-update.component';

describe('BioProfile Management Update Component', () => {
  let comp: BioProfileUpdateComponent;
  let fixture: ComponentFixture<BioProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bioProfileService: BioProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BioProfileUpdateComponent],
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
      .overrideTemplate(BioProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BioProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bioProfileService = TestBed.inject(BioProfileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bioProfile: IBioProfile = { id: 456 };

      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bioProfile));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BioProfile>>();
      const bioProfile = { id: 123 };
      jest.spyOn(bioProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bioProfile }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bioProfileService.update).toHaveBeenCalledWith(bioProfile);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BioProfile>>();
      const bioProfile = new BioProfile();
      jest.spyOn(bioProfileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bioProfile }));
      saveSubject.complete();

      // THEN
      expect(bioProfileService.create).toHaveBeenCalledWith(bioProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BioProfile>>();
      const bioProfile = { id: 123 };
      jest.spyOn(bioProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bioProfileService.update).toHaveBeenCalledWith(bioProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
