package org.kehboard.entity.json.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ApiKeyJSON {
    @JsonProperty
    private String key;
}
