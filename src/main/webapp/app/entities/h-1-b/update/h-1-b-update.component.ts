import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IH1B, H1B } from '../h-1-b.model';
import { H1BService } from '../service/h-1-b.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { GenderType } from 'app/entities/enumerations/gender-type.model';
import { CategoryType } from 'app/entities/enumerations/category-type.model';

@Component({
  selector: 'jhi-h-1-b-update',
  templateUrl: './h-1-b-update.component.html',
})
export class H1BUpdateComponent implements OnInit {
  isSaving = false;
  genderTypeValues = Object.keys(GenderType);
  categoryTypeValues = Object.keys(CategoryType);

  editForm = this.fb.group({
    id: [],
    userId: [],
    firstName: [],
    middleName: [],
    lastName: [],
    dateOfBirth: [],
    countryOfBirth: [],
    countryOfCitizenShip: [],
    passportNumber: [],
    gender: [],
    category: [],
    email: [],
    currentAddress: [],
    phoneNumber: [],
    bachelorDegree: [],
    year: [],
    currentVisaStatus: [],
    referedBy: [],
    passportFrontPage: [],
    passportBackPage: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected h1BService: H1BService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ h1B }) => {
      if (h1B.id === undefined) {
        const today = dayjs().startOf('day');
        h1B.dateOfBirth = today;
      }

      this.updateForm(h1B);
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
    const h1B = this.createFromForm();
    if (h1B.id !== undefined) {
      this.subscribeToSaveResponse(this.h1BService.update(h1B));
    } else {
      this.subscribeToSaveResponse(this.h1BService.create(h1B));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IH1B>>): void {
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

  protected updateForm(h1B: IH1B): void {
    this.editForm.patchValue({
      id: h1B.id,
      userId: h1B.userId,
      firstName: h1B.firstName,
      middleName: h1B.middleName,
      lastName: h1B.lastName,
      dateOfBirth: h1B.dateOfBirth ? h1B.dateOfBirth.format(DATE_TIME_FORMAT) : null,
      countryOfBirth: h1B.countryOfBirth,
      countryOfCitizenShip: h1B.countryOfCitizenShip,
      passportNumber: h1B.passportNumber,
      gender: h1B.gender,
      category: h1B.category,
      email: h1B.email,
      currentAddress: h1B.currentAddress,
      phoneNumber: h1B.phoneNumber,
      bachelorDegree: h1B.bachelorDegree,
      year: h1B.year,
      currentVisaStatus: h1B.currentVisaStatus,
      referedBy: h1B.referedBy,
      passportFrontPage: h1B.passportFrontPage,
      passportBackPage: h1B.passportBackPage,
    });
  }

  protected createFromForm(): IH1B {
    return {
      ...new H1B(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value
        ? dayjs(this.editForm.get(['dateOfBirth'])!.value, DATE_TIME_FORMAT)
        : undefined,
      countryOfBirth: this.editForm.get(['countryOfBirth'])!.value,
      countryOfCitizenShip: this.editForm.get(['countryOfCitizenShip'])!.value,
      passportNumber: this.editForm.get(['passportNumber'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      category: this.editForm.get(['category'])!.value,
      email: this.editForm.get(['email'])!.value,
      currentAddress: this.editForm.get(['currentAddress'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      bachelorDegree: this.editForm.get(['bachelorDegree'])!.value,
      year: this.editForm.get(['year'])!.value,
      currentVisaStatus: this.editForm.get(['currentVisaStatus'])!.value,
      referedBy: this.editForm.get(['referedBy'])!.value,
      passportFrontPage: this.editForm.get(['passportFrontPage'])!.value,
      passportBackPage: this.editForm.get(['passportBackPage'])!.value,
    };
  }
}
