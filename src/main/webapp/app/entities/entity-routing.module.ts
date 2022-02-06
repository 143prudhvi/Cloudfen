import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'login-profile',
        data: { pageTitle: 'cloudfenApp.loginProfile.home.title' },
        loadChildren: () => import('./login-profile/login-profile.module').then(m => m.LoginProfileModule),
      },
      {
        path: 'bio-profile',
        data: { pageTitle: 'cloudfenApp.bioProfile.home.title' },
        loadChildren: () => import('./bio-profile/bio-profile.module').then(m => m.BioProfileModule),
      },
      {
        path: 'h-1-b',
        data: { pageTitle: 'cloudfenApp.h1B.home.title' },
        loadChildren: () => import('./h-1-b/h-1-b.module').then(m => m.H1BModule),
      },
      {
        path: 'bachelor-degree',
        data: { pageTitle: 'cloudfenApp.bachelorDegree.home.title' },
        loadChildren: () => import('./bachelor-degree/bachelor-degree.module').then(m => m.BachelorDegreeModule),
      },
      {
        path: 'file-details',
        data: { pageTitle: 'cloudfenApp.fileDetails.home.title' },
        loadChildren: () => import('./file-details/file-details.module').then(m => m.FileDetailsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
