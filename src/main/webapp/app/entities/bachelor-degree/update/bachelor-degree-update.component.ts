import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBachelorDegree, BachelorDegree } from '../bachelor-degree.model';
import { BachelorDegreeService } from '../service/bachelor-degree.service';

@Component({
  selector: 'jhi-bachelor-degree-update',
  templateUrl: './bachelor-degree-update.component.html',
})
export class BachelorDegreeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    course: [],
  });

  constructor(
    protected bachelorDegreeService: BachelorDegreeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bachelorDegree }) => {
      this.updateForm(bachelorDegree);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bachelorDegree = this.createFromForm();
    if (bachelorDegree.id !== undefined) {
      this.subscribeToSaveResponse(this.bachelorDegreeService.update(bachelorDegree));
    } else {
      this.subscribeToSaveResponse(this.bachelorDegreeService.create(bachelorDegree));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBachelorDegree>>): void {
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

  protected updateForm(bachelorDegree: IBachelorDegree): void {
    this.editForm.patchValue({
      id: bachelorDegree.id,
      course: bachelorDegree.course,
    });
  }

  protected createFromForm(): IBachelorDegree {
    return {
      ...new BachelorDegree(),
      id: this.editForm.get(['id'])!.value,
      course: this.editForm.get(['course'])!.value,
    };
  }
}
