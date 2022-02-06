import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBachelorDegree } from '../bachelor-degree.model';
import { BachelorDegreeService } from '../service/bachelor-degree.service';

@Component({
  templateUrl: './bachelor-degree-delete-dialog.component.html',
})
export class BachelorDegreeDeleteDialogComponent {
  bachelorDegree?: IBachelorDegree;

  constructor(protected bachelorDegreeService: BachelorDegreeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bachelorDegreeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
