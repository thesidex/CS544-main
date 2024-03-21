package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.AccountType;
import edu.miu.cs.cs544.domain.Event;
import edu.miu.cs.cs544.domain.Location;
import lombok.Data;

import java.io.Serializable;
@Data
public class ScannerPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String code;
    private Location location;
    private AccountType accountType;
    private Event event;

    public ScannerPayload(String code, Location location, AccountType accountType, Event event) {
        this.code = code;
        this.location = location;
        this.accountType = accountType;
        this.event = event;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
