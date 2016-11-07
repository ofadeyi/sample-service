package uk.co.whitbread.sample.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class ErrorBean {
    @ApiModelProperty(example = "001", required = true)
    private String code;

    @ApiModelProperty(example = "['Error message']", required = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> details = new ArrayList<>();

    public ErrorBean(String code, List<String> details) {
        this.code = code;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ErrorBean{");
        sb.append("code='").append(code).append('\'');
        sb.append(", details=").append(details);
        sb.append('}');
        return sb.toString();
    }
}