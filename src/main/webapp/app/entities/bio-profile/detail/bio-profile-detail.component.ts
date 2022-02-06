import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBioProfile } from '../bio-profile.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-bio-profile-detail',
  templateUrl: './bio-profile-detail.component.html',
})
export class BioProfileDetailComponent implements OnInit {
  bioProfile: IBioProfile | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bioProfile }) => {
      this.bioProfile = bioProfile;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
