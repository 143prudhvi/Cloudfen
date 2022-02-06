import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BachelorDegreeService } from '../service/bachelor-degree.service';

import { BachelorDegreeComponent } from './bachelor-degree.component';

describe('BachelorDegree Management Component', () => {
  let comp: BachelorDegreeComponent;
  let fixture: ComponentFixture<BachelorDegreeComponent>;
  let service: BachelorDegreeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BachelorDegreeComponent],
    })
      .overrideTemplate(BachelorDegreeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BachelorDegreeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BachelorDegreeService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.bachelorDegrees?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
