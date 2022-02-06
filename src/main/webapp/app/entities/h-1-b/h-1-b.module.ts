import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { H1BComponent } from './list/h-1-b.component';
import { H1BDetailComponent } from './detail/h-1-b-detail.component';
import { H1BUpdateComponent } from './update/h-1-b-update.component';
import { H1BDeleteDialogComponent } from './delete/h-1-b-delete-dialog.component';
import { H1BRoutingModule } from './route/h-1-b-routing.module';

@NgModule({
  imports: [SharedModule, H1BRoutingModule],
  declarations: [H1BComponent, H1BDetailComponent, H1BUpdateComponent, H1BDeleteDialogComponent],
  entryComponents: [H1BDeleteDialogComponent],
})
export class H1BModule {}
