import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IH1B } from '../h-1-b.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-h-1-b-detail',
  templateUrl: './h-1-b-detail.component.html',
})
export class H1BDetailComponent implements OnInit {
  h1B: IH1B | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ h1B }) => {
      this.h1B = h1B;
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
