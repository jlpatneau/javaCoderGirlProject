import java.math.BigDecimal;

public class OrderProduct {
    private int orderId;
    private Products product;
    private Color color;
    private int qty;
    private BigDecimal salePrice;

    public OrderProduct(int orderId) {
        this.orderId = orderId;
    }

    public OrderProduct(int orderId, Products product, Color color, int qty, BigDecimal salePrice) {
        this.orderId = orderId;
        this.product = product;
        this.color = color;
        this.qty = qty;
        this.salePrice = salePrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}
