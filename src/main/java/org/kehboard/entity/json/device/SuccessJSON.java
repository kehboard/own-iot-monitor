package org.kehboard.entity.json.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class SuccessJSON {
    @JsonProperty
    private Boolean ok = true;
}
