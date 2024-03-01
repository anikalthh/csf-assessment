package vttp.batch4.csf.ecommerce.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.ecommerce.exceptions.PurchaseOrderException;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  // You may only add Exception to the method's signature
  public Boolean create(Order order) throws PurchaseOrderException {
    System.out.printf("\nINSERT BOTH");

    // TODO Task 3
    Boolean orderDetailsSuccess = insertOrderDetails(order);
    Boolean lineItemsSuccess = insertLineItems(order);
    // insertOrderDetails(order);
    // insertLineItems(order);

    if (!orderDetailsSuccess || !lineItemsSuccess) {
      throw new PurchaseOrderException();
    } else {
      return true;
    }
  }

  // INSERT ORDER DETAILS
  public Boolean insertOrderDetails(Order order) {
    
    return (template.update(Queries.SQL_INSERT_ORDER_DETAILS, order.getOrderId(), order.getDate(), order.getName(), order.getAddress(), order.getPriority(), order.getComments())) == 1;

  }

  // INSERT ALL LINE ITEMS IN THE ORDER
  public Boolean insertLineItems(Order order) {
    System.out.printf("\nINSERT LINE ITEMS: %s\n\n", order.getOrderId());

    Cart cart = order.getCart();
    int itemsInserted = 0;
    int success;
    for (LineItem li : cart.getLineItems()) {
      success = template.update(Queries.SQL_INSERT_LINE_ITEM, li.getProductId(), li.getName(), li.getQuantity(), li.getPrice(), order.getOrderId());
      itemsInserted += success;
    }
    // System.out.printf("\n\nINSERT ORDER DETAILS: SHOULD BE %s: %s\n\n", cart.getLineItems().size(), itemsInserted);

    return itemsInserted == cart.getLineItems().size();
  }
}
