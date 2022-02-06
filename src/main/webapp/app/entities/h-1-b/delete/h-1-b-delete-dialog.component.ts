import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IH1B } from '../h-1-b.model';
import { H1BService } from '../service/h-1-b.service';

@Component({
  templateUrl: './h-1-b-delete-dialog.component.html',
})
export class H1BDeleteDialogComponent {
  h1B?: IH1B;

  constructor(protected h1BService: H1BService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.h1BService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
