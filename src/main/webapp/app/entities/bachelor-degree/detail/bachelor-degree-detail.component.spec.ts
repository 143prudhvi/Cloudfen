import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BachelorDegreeDetailComponent } from './bachelor-degree-detail.component';

describe('BachelorDegree Management Detail Component', () => {
  let comp: BachelorDegreeDetailComponent;
  let fixture: ComponentFixture<BachelorDegreeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BachelorDegreeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bachelorDegree: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BachelorDegreeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BachelorDegreeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bachelorDegree on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bachelorDegree).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
