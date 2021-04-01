package org.kehboard.entity.json.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class MeasureJSON {
    @JsonProperty
    private String variable;
    @JsonProperty
    private Float data;
}