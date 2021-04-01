package org.kehboard.entity.json.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.LinkedList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ReceiveMeasurement {
    @JsonProperty
    private String key;
    @JsonProperty
    private LinkedList<MeasureJSON> measure;
}