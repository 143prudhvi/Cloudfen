import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFileDetails, FileDetails } from '../file-details.model';
import { FileDetailsService } from '../service/file-details.service';

@Component({
  selector: 'jhi-file-details-update',
  templateUrl: './file-details-update.component.html',
})
export class FileDetailsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [],
    userName: [],
    type: [],
    fileName: [],
  });

  constructor(protected fileDetailsService: FileDetailsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileDetails }) => {
      this.updateForm(fileDetails);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fileDetails = this.createFromForm();
    if (fileDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.fileDetailsService.update(fileDetails));
    } else {
      this.subscribeToSaveResponse(this.fileDetailsService.create(fileDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileDetails>>): void {
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

  protected updateForm(fileDetails: IFileDetails): void {
    this.editForm.patchValue({
      id: fileDetails.id,
      userId: fileDetails.userId,
      userName: fileDetails.userName,
      type: fileDetails.type,
      fileName: fileDetails.fileName,
    });
  }

  protected createFromForm(): IFileDetails {
    return {
      ...new FileDetails(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      type: this.editForm.get(['type'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
    };
  }
}
