import java.time.LocalDate;

public class Inventory {

    private int prodId;
    private int colorId;
    private int availableQty;
    private int soldQty;
    private LocalDate asOfDate;

    public Inventory(int prodId, int colorId, int availableQty, int soldQty, LocalDate asOfDate) {
        this.prodId = prodId;
        this.colorId = colorId;
        this.availableQty = availableQty;
        this.soldQty = soldQty;
        this.asOfDate = asOfDate;
    }

    public Inventory(int prodId, int colorId) {
        this.prodId = prodId;
        this.colorId = colorId;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public int getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(int soldQty) {
        this.soldQty = soldQty;
    }

    public LocalDate getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(LocalDate asOfDate) {
        this.asOfDate = asOfDate;
    }
}
