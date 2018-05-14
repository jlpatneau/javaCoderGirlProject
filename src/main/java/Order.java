import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private int orderId;
    LocalDate orderDate;
    private Customer customer;
    private BigDecimal orderTotalAmt;
    private Location location;
    private String pymtType;
    private BigDecimal taxableAmt;
    private BigDecimal salesTaxAmt;
    LocalDate deliveryDate;
    private BigDecimal paidAmt;
    private BigDecimal discountAmt;
    private String orderNote;


    public Order(int orderId, LocalDate orderDate, Customer customer, BigDecimal orderTotalAmt, Location location, String pymtType, BigDecimal taxableAmt, BigDecimal salesTaxAmt, LocalDate deliveryDate, BigDecimal paidAmt, BigDecimal discountAmt, String orderNote) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.orderTotalAmt = orderTotalAmt;
        this.location = location;
        this.pymtType = pymtType;
        this.taxableAmt = taxableAmt;
        this.salesTaxAmt = salesTaxAmt;
        this.deliveryDate = deliveryDate;
        this.paidAmt = paidAmt;
        this.discountAmt = discountAmt;
        this.orderNote = orderNote;
    }

    public Order(int orderId) {
        this.orderId = orderId;
    }


    public Order(int orderId, LocalDate orderDate, Customer customer, BigDecimal orderTotalAmt, LocalDate deliveryDate, BigDecimal paidAmt) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.orderTotalAmt = orderTotalAmt;
        this.deliveryDate = deliveryDate;
        this.paidAmt = paidAmt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getOrderTotalAmt() {
        return orderTotalAmt;
    }

    public void setOrderTotalAmt(BigDecimal orderTotalAmt) {
        this.orderTotalAmt = orderTotalAmt;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPymtType() {
        return pymtType;
    }

    public void setPymtType(String pymtType) {
        this.pymtType = pymtType;
    }

    public BigDecimal getTaxableAmt() {
        return taxableAmt;
    }

    public void setTaxableAmt(BigDecimal taxableAmt) {
        this.taxableAmt = taxableAmt;
    }

    public BigDecimal getSalesTaxAmt() {
        return salesTaxAmt;
    }

    public void setSalesTaxAmt(BigDecimal salesTaxAmt) {
        this.salesTaxAmt = salesTaxAmt;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        this.paidAmt = paidAmt;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }
}
