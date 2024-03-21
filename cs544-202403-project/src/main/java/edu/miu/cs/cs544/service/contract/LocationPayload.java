package edu.miu.cs.cs544.service.contract;

import edu.miu.cs.cs544.domain.LocationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String description;
    private LocationType type;
}
