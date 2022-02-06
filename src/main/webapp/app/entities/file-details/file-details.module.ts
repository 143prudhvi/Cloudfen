import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FileDetailsComponent } from './list/file-details.component';
import { FileDetailsDetailComponent } from './detail/file-details-detail.component';
import { FileDetailsUpdateComponent } from './update/file-details-update.component';
import { FileDetailsDeleteDialogComponent } from './delete/file-details-delete-dialog.component';
import { FileDetailsRoutingModule } from './route/file-details-routing.module';

@NgModule({
  imports: [SharedModule, FileDetailsRoutingModule],
  declarations: [FileDetailsComponent, FileDetailsDetailComponent, FileDetailsUpdateComponent, FileDetailsDeleteDialogComponent],
  entryComponents: [FileDetailsDeleteDialogComponent],
})
export class FileDetailsModule {}
