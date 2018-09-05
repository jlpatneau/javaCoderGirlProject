public class Color {
    private int colorId;
    private String colorName;

    public Color(String colorName) {
        this.colorName = colorName;
    }

    public Color(int colorId, String colorName) {
        this.colorId = colorId;
        this.colorName = colorName;
    }

    public Color(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
