import java.math.BigDecimal;
import java.time.LocalDate;

public class Location {
    private int locationId;
    private String locationName;
    private BigDecimal taxRate;
    private LocalDate taxStartDate;
    private LocalDate taxEndDate;

    public Location(int locationId, String locationName, BigDecimal taxRate, LocalDate taxStartDate, LocalDate taxEndDate) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.taxRate = taxRate;
        this.taxStartDate = taxStartDate;
        this.taxEndDate = taxEndDate;
    }

    public Location(String locationName, BigDecimal taxRate, LocalDate taxStartDate, LocalDate taxEndDate) {
        this.locationName = locationName;
        this.taxRate = taxRate;
        this.taxStartDate = taxStartDate;
        this.taxEndDate = taxEndDate;
    }

    public Location(int locationId) {
        this.locationId = locationId;
    }

    public Location(int locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public Location(int locationId, String locationName, BigDecimal taxRate) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.taxRate = taxRate;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public LocalDate getTaxStartDate() {
        return taxStartDate;
    }

    public void setTaxStartDate(LocalDate taxStartDate) {
        this.taxStartDate = taxStartDate;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public LocalDate getTaxEndDate() {
        return taxEndDate;
    }

    public void setTaxEndDate(LocalDate taxEndDate) {
        this.taxEndDate = taxEndDate;
    }
}
