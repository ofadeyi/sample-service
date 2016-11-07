package uk.co.whitbread.sample.model;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
public class SampleRequest {

    private String sampleString;
    private String sampleDate;
    private boolean sampleBoolean;

    public String getSampleString() {
        return sampleString;
    }

    public void setSampleString(String sampleString) {
        this.sampleString = sampleString;
    }

    public String getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(String sampleDate) {
        this.sampleDate = sampleDate;
    }

    public boolean isSampleBoolean() {
        return sampleBoolean;
    }

    public void setSampleBoolean(boolean sampleBoolean) {
        this.sampleBoolean = sampleBoolean;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SampleRequest{");
        sb.append("sampleString='").append(sampleString).append('\'');
        sb.append(", sampleDate='").append(sampleDate).append('\'');
        sb.append(", sampleBoolean=").append(sampleBoolean);
        sb.append('}');
        return sb.toString();
    }
}
