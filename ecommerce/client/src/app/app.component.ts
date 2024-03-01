import { Component, OnInit, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  // NOTE: you are free to modify this component

  private router = inject(Router)
  private cartStore = inject(CartStore)

  itemCount!: number
  isCartValid!: boolean

  ngOnInit(): void {
    this.cartStore.getNumberOfItems.subscribe(
      (value) => {
        this.itemCount = value
        console.log('item count: ', this.itemCount)
        if (this.itemCount == 0) {
          console.log('false')
          this.isCartValid = false
        } else {
          console.log('true')

          this.isCartValid = true
        }
      }
    )
  }

  checkout(): void {
    this.router.navigate(['/checkout'])
  }

  
}
