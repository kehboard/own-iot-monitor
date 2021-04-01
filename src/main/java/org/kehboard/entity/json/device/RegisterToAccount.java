package org.kehboard.entity.json.device;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class RegisterToAccount {

    @JsonProperty
    private int sharedDevId;
    @JsonProperty
    private String token;
    @JsonProperty
    private String password;
}
