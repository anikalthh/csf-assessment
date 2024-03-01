package vttp.batch4.csf.ecommerce.models;

import java.util.Date;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

// IMPORTANT: DO NOT MODIFY THIS CLASS
// If this class is changed, any assessment task relying on this class will
// not be marked
public class Order {
  private final String orderId;
  private Date date = new Date();
  private String name;
  private String address;
  private boolean priority;
  private String comments;
  private Cart cart = new Cart();

  public Order() {
    // 26 characters
    // Eg - 01HNS6YMJNZX24G4YN38AGBZEE
    orderId = UlidCreator.getMonotonicUlid().toString();
    System.out.printf("\n\n\n----NEW ORDER CREATED----\n%s\n\n", orderId);
  }

  public String getOrderId() { return this.orderId; }

  public void setDate(Date date) { this.date = date; }
  public Date getDate() { return this.date; }

  public void setName(String name) { this.name = name; }
  public String getName() { return this.name; }

  public void setAddress(String address) { this.address = address; }
  public String getAddress() { return this.address; }

  public void setPriority(boolean priority) { this.priority = priority; }
  public boolean getPriority() { return this.priority; }
  public boolean isPriority() { return this.priority; }

  public void setComments(String comments) { this.comments = comments; }
  public String getComments() { return this.comments; }

  public void setCart(Cart cart) { this.cart = cart; }
  public Cart getCart() { return this.cart; }

  @Override
  public String toString() {
    return "Order{orderId=%s, date=%s, name=%s, address=%s, priority=%b, comments=%s, cart=%s}"
      .formatted(orderId, date, name, address, priority, comments, cart);
  }

  public static Order fromJson(JsonObject jsonObj) {
    String name = jsonObj.getString("name");
    String address = jsonObj.getString("address");
    Boolean priority = jsonObj.getBoolean("priority");
    String comments = jsonObj.getString("comments");
    JsonArray cartArray = jsonObj.getJsonObject("cart").getJsonArray("lineItems");
    
    Order order = new Order();
    Cart cart = new Cart();

    for (JsonValue jVal : cartArray) {
      JsonObject j = jVal.asJsonObject();
      // Json.createReader(new StringReader(jVal.toString())).readObject();
      String prodId = j.getString("prodId");
      int qty = j.getInt("quantity");
      String prodName = j.getString("name");
      float price = j.getJsonNumber("price").bigDecimalValue().floatValue();

      LineItem li = new LineItem();
      li.setName(prodName);
      li.setPrice(price);
      li.setProductId(prodId);
      li.setQuantity(qty);
      cart.addLineItem(li);
    }

    System.out.printf("CART INITIALISED: %s\n\n", cart);

    order.setName(name);
    order.setAddress(address);
    order.setDate(new Date());
    order.setPriority(priority);
    order.setComments(comments);
    order.setCart(cart);
    
    System.out.printf("ORDER INITIALISED: %s\n\n", order);
    return order;

  }
}
