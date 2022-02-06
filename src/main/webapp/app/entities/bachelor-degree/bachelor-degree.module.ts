import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BachelorDegreeComponent } from './list/bachelor-degree.component';
import { BachelorDegreeDetailComponent } from './detail/bachelor-degree-detail.component';
import { BachelorDegreeUpdateComponent } from './update/bachelor-degree-update.component';
import { BachelorDegreeDeleteDialogComponent } from './delete/bachelor-degree-delete-dialog.component';
import { BachelorDegreeRoutingModule } from './route/bachelor-degree-routing.module';

@NgModule({
  imports: [SharedModule, BachelorDegreeRoutingModule],
  declarations: [
    BachelorDegreeComponent,
    BachelorDegreeDetailComponent,
    BachelorDegreeUpdateComponent,
    BachelorDegreeDeleteDialogComponent,
  ],
  entryComponents: [BachelorDegreeDeleteDialogComponent],
})
export class BachelorDegreeModule {}
