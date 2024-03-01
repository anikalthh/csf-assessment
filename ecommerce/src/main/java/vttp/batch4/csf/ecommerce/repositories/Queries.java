package vttp.batch4.csf.ecommerce.repositories;

public class Queries {
    public static final String SQL_INSERT_LINE_ITEM = """
        INSERT INTO LINE_ITEM(product_id, name, quantity, price, order_id)
        VALUES (?, ?, ?, ?, ?)
    """;

    public static final String SQL_INSERT_ORDER_DETAILS = """
        INSERT INTO ORDER_DETAILS(order_id, order_date, name, address, priority, comments)
        VALUES (?, ?, ?, ?, ?, ?)        
    """;
}
