import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ILoginProfile, LoginProfile } from '../login-profile.model';
import { LoginProfileService } from '../service/login-profile.service';

@Component({
  selector: 'jhi-login-profile-update',
  templateUrl: './login-profile-update.component.html',
})
export class LoginProfileUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userName: [],
    userId: [],
    memberId: [],
    phoneNumber: [],
    emailId: [],
    password: [],
    status: [],
    activationCode: [],
  });

  constructor(protected loginProfileService: LoginProfileService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loginProfile }) => {
      this.updateForm(loginProfile);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loginProfile = this.createFromForm();
    if (loginProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.loginProfileService.update(loginProfile));
    } else {
      this.subscribeToSaveResponse(this.loginProfileService.create(loginProfile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoginProfile>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(loginProfile: ILoginProfile): void {
    this.editForm.patchValue({
      id: loginProfile.id,
      userName: loginProfile.userName,
      userId: loginProfile.userId,
      memberId: loginProfile.memberId,
      phoneNumber: loginProfile.phoneNumber,
      emailId: loginProfile.emailId,
      password: loginProfile.password,
      status: loginProfile.status,
      activationCode: loginProfile.activationCode,
    });
  }

  protected createFromForm(): ILoginProfile {
    return {
      ...new LoginProfile(),
      id: this.editForm.get(['id'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      memberId: this.editForm.get(['memberId'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      emailId: this.editForm.get(['emailId'])!.value,
      password: this.editForm.get(['password'])!.value,
      status: this.editForm.get(['status'])!.value,
      activationCode: this.editForm.get(['activationCode'])!.value,
    };
  }
}
