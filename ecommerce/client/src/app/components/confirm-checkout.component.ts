import { Component, OnInit, inject } from '@angular/core';
import { LineItem } from '../models';
import { Observable } from 'rxjs';
import { CartStore } from '../cart.store';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-confirm-checkout',
  templateUrl: './confirm-checkout.component.html',
  styleUrl: './confirm-checkout.component.css'
})
export class ConfirmCheckoutComponent implements OnInit {

  // dependencies
  private cartStore = inject(CartStore)
  private fb = inject(FormBuilder)

  // vars
  lineItems$!: Observable<LineItem[]>
  totalAmount!: number
  form!: FormGroup
  numberOfItems!: number
  // isFormValid!: boolean

  // lifecycle hooks
  ngOnInit(): void {
    this.form = this.createForm()
    this.lineItems$ = this.cartStore.getAllLineItems
    console.log('>>> getting all line items from component store to display', this.lineItems$)

    // get number of items
    this.cartStore.getNumberOfItems.subscribe(
      (numItems) => {
        this.numberOfItems = numItems
      }
    )

    // calculate total cart amount
    this.totalAmount = 0 // reset
    this.lineItems$.subscribe(
      (cart) => {
        for (let lineItem of cart) {
          this.totalAmount += lineItem.price
        }
      }
    )
  }

  // TODO Task 3
  // create form
  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      address: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      priority: this.fb.control<boolean>(false),
      comments: this.fb.control<string>('')
    })
  }

  isFormValid() : boolean {
    return (this.numberOfItems > 0) && this.form.valid
  }

}

