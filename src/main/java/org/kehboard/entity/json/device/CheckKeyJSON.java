package org.kehboard.entity.json.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class CheckKeyJSON {
    @JsonProperty
    private String key;
}
