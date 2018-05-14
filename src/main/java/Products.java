import java.math.BigDecimal;

public class Products {
    private int prodId;
    private String prodName;
    private BigDecimal prodPrice;

    public Products(int prodId, String prodName, BigDecimal prodPrice) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }

    public Products(String prodName, BigDecimal prodPrice) {
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }

    public Products(int prodId) {
        this.prodId = prodId;
    }

    public Products(int prodId, String prodName) {
        this.prodId = prodId;
        this.prodName = prodName;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public BigDecimal getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(BigDecimal prodPrice) {
        this.prodPrice = prodPrice;
    }
}
