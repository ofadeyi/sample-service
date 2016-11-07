package uk.co.whitbread.sample.model;

/**
 * Created by Oleksandr Murha on 07/11/2016.
 */
public class SampleResponse {

    private String status;
    private String response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SampleResponse{");
        sb.append("status='").append(status).append('\'');
        sb.append(", response='").append(response).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
