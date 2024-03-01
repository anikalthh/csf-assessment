package vttp.batch4.csf.ecommerce.repositories;

public class Queries {
    public static final String SQL_INSERT_LINE_ITEM = """
        INSERT INTO line_item(product_id, name, quantity, price, order_id)
        VALUES (?, ?, ?, ?, ?);
    """;

    public static final String SQL_INSERT_ORDER_DETAILS = """
        INSERT INTO order_details(order_id, order_date, name, address, priority, comments)
        VALUES (?, ?, ?, ?, ?, ?);        
    """;
}
