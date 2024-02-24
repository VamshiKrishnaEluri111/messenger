import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutboxComponent } from './outbox.component';

describe('OutboxComponent', () => {
  let component: OutboxComponent;
  let fixture: ComponentFixture<OutboxComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OutboxComponent]
    });
    fixture = TestBed.createComponent(OutboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});