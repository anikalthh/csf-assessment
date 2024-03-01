package vttp.batch4.csf.ecommerce.controllers;

import java.io.StringReader;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch4.csf.ecommerce.exceptions.PurchaseOrderException;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.services.PurchaseOrderService;

@Controller
@RequestMapping("/api")
public class OrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  @PostMapping(path = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postOrder(@RequestBody String payload) throws PurchaseOrderException {

    // TODO Task 3
    JsonObject jsonObjOrder = Json.createReader(new StringReader(payload)).readObject();
    System.out.printf("\nCONTROLLER RECEIVED ORDER: %s\n\n", jsonObjOrder);

    Order order = Order.fromJson(jsonObjOrder);

    // SAVE ORDER INTO REPO
    try {
      poSvc.createNewPurchaseOrder(order);
    } catch (PurchaseOrderException e) {
      JsonObject badReq = Json.createObjectBuilder()
        .add("errorMsg", "Unable to process order")
        .build();

      return ResponseEntity.badRequest().body(badReq.toString());
    }

    JsonObject okResp = Json.createObjectBuilder()
        .add("orderId", order.getOrderId())
        .build();

    return ResponseEntity.ok().body(okResp.toString());
  }
}
