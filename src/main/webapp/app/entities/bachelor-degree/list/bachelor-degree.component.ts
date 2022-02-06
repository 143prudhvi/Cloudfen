import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBachelorDegree } from '../bachelor-degree.model';
import { BachelorDegreeService } from '../service/bachelor-degree.service';
import { BachelorDegreeDeleteDialogComponent } from '../delete/bachelor-degree-delete-dialog.component';

@Component({
  selector: 'jhi-bachelor-degree',
  templateUrl: './bachelor-degree.component.html',
})
export class BachelorDegreeComponent implements OnInit {
  bachelorDegrees?: IBachelorDegree[];
  isLoading = false;

  constructor(protected bachelorDegreeService: BachelorDegreeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.bachelorDegreeService.query().subscribe({
      next: (res: HttpResponse<IBachelorDegree[]>) => {
        this.isLoading = false;
        this.bachelorDegrees = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBachelorDegree): number {
    return item.id!;
  }

  delete(bachelorDegree: IBachelorDegree): void {
    const modalRef = this.modalService.open(BachelorDegreeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bachelorDegree = bachelorDegree;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
