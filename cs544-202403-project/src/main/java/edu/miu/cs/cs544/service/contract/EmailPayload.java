package edu.miu.cs.cs544.service.contract;

import lombok.Data;

import java.io.Serializable;
@Data
public class EmailPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private String to;
    private String text;

    public EmailPayload(String to, String text) {
        this.to = to;
        this.text = text;
    }
}
