
// TODO Task 2

import { Injectable } from "@angular/core";
import Dexie from "dexie";
import { Cart, LineItem } from "./models";
import { ComponentStore } from "@ngrx/component-store";

const INIT_STORE : Cart = {
    lineItems: []
}

// Use the following class to implement your store
@Injectable()
export class CartStore extends ComponentStore<Cart>{
    constructor() { super(INIT_STORE) }


    // ADD TO CART
    readonly addToCart = this.updater<LineItem>(
        (slice: Cart, lineItem: LineItem) => {
            return {
                lineItems: [ ...slice.lineItems, lineItem]
            }
        }
    )

    // GET ALL ITEMS IN CART
    // readonly getAllLineItems = this.select<LineItem[]>(
    //     (slice: Cart) => {
    //         console.log('getAllLineItems Comp store')
    //         return slice.lineItems
    //     }
    // )
    readonly getAllLineItems = this.select<LineItem[]>(
        (slice: Cart) => slice.lineItems
    )

    // GET NUMBER OF ITEMS IN CART
    readonly getNumberOfItems = this.select<number>(
        (slice: Cart) => slice.lineItems.length
    )

    // // TOTAL SUM OF ITEMS' PRICES
    // readonly getTotalAmount = this.select<number>(
    //     (slice: Cart) => {
    //         for (lineItem : LineItem)
    //     }
    // )

    // DELETE FROM CART
    readonly deleteFromCart = (prodId: string) => {
        return this.select<LineItem[]>(
            (slice: Cart) => {
                return slice.lineItems.filter(lineItem => lineItem.prodId != prodId)
            }
        )
    }
}
