package org.kehboard.entity.json.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class GetMeasureJSON {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private long unixtime;
    @JsonProperty
    private Float value;
}
