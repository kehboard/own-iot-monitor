package org.kehboard.entity.json.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class MeasureNameJSON {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String measureName;
    @JsonProperty
    private String measureSymbol;
    @JsonProperty
    private String iotName;
    @JsonProperty
    private String description;
}
