package mastersidi.fste.umi.ac.moroccotours;



public class DataClass {

    private String dataTitle;
    private int dataDesc;
    private int dataImage;
    private String latitude;
    private String longitude;

    public String getDataTitle() {
        return dataTitle;
    }

    public int getDataDesc() {
        return dataDesc;
    }

    public int getDataImage() {
        return dataImage;
    }
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public DataClass(String dataTitle, int dataDesc, int dataImage, String latitude, String longitude) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataImage = dataImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}