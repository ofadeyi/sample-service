package uk.co.whitbread.sample.model;

import org.hibernate.validator.constraints.NotEmpty;
import uk.co.whitbread.sample.validation.DateFormat;

import javax.validation.constraints.NotNull;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
public class SampleRequest {

    @NotEmpty
    private String sampleString;
    @DateFormat
    @NotEmpty
    private String sampleDate;
    @NotNull
    private Boolean sampleBoolean;

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

    public Boolean getSampleBoolean() {
        return sampleBoolean;
    }

    public void setSampleBoolean(Boolean sampleBoolean) {
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
