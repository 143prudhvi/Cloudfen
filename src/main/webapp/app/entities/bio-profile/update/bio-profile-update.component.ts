import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBioProfile, BioProfile } from '../bio-profile.model';
import { BioProfileService } from '../service/bio-profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-bio-profile-update',
  templateUrl: './bio-profile-update.component.html',
})
export class BioProfileUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userName: [],
    userId: [],
    memberId: [],
    firstName: [],
    lastName: [],
    dob: [],
    gender: [],
    imageUrl: [],
    title: [],
    summary: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected bioProfileService: BioProfileService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bioProfile }) => {
      this.updateForm(bioProfile);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('cloudfenApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bioProfile = this.createFromForm();
    if (bioProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.bioProfileService.update(bioProfile));
    } else {
      this.subscribeToSaveResponse(this.bioProfileService.create(bioProfile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBioProfile>>): void {
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

  protected updateForm(bioProfile: IBioProfile): void {
    this.editForm.patchValue({
      id: bioProfile.id,
      userName: bioProfile.userName,
      userId: bioProfile.userId,
      memberId: bioProfile.memberId,
      firstName: bioProfile.firstName,
      lastName: bioProfile.lastName,
      dob: bioProfile.dob,
      gender: bioProfile.gender,
      imageUrl: bioProfile.imageUrl,
      title: bioProfile.title,
      summary: bioProfile.summary,
    });
  }

  protected createFromForm(): IBioProfile {
    return {
      ...new BioProfile(),
      id: this.editForm.get(['id'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      memberId: this.editForm.get(['memberId'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      title: this.editForm.get(['title'])!.value,
      summary: this.editForm.get(['summary'])!.value,
    };
  }
}
