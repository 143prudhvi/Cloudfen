import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBachelorDegree } from '../bachelor-degree.model';

@Component({
  selector: 'jhi-bachelor-degree-detail',
  templateUrl: './bachelor-degree-detail.component.html',
})
export class BachelorDegreeDetailComponent implements OnInit {
  bachelorDegree: IBachelorDegree | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bachelorDegree }) => {
      this.bachelorDegree = bachelorDegree;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
