import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoginProfileService } from '../service/login-profile.service';
import { ILoginProfile, LoginProfile } from '../login-profile.model';

import { LoginProfileUpdateComponent } from './login-profile-update.component';

describe('LoginProfile Management Update Component', () => {
  let comp: LoginProfileUpdateComponent;
  let fixture: ComponentFixture<LoginProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loginProfileService: LoginProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoginProfileUpdateComponent],
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
      .overrideTemplate(LoginProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoginProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loginProfileService = TestBed.inject(LoginProfileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const loginProfile: ILoginProfile = { id: 456 };

      activatedRoute.data = of({ loginProfile });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(loginProfile));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LoginProfile>>();
      const loginProfile = { id: 123 };
      jest.spyOn(loginProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loginProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loginProfile }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(loginProfileService.update).toHaveBeenCalledWith(loginProfile);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LoginProfile>>();
      const loginProfile = new LoginProfile();
      jest.spyOn(loginProfileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loginProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loginProfile }));
      saveSubject.complete();

      // THEN
      expect(loginProfileService.create).toHaveBeenCalledWith(loginProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LoginProfile>>();
      const loginProfile = { id: 123 };
      jest.spyOn(loginProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loginProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loginProfileService.update).toHaveBeenCalledWith(loginProfile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
