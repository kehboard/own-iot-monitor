package org.kehboard.entity.json.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class MeasureRequestJSON {
    @JsonProperty
    private List<MeasureNameJSON> measureNames;
    @JsonProperty
    private Map<Integer, List<GetMeasureJSON>> measures;
}
