package org.kehboard.entity.json.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ChangePassword {
    @JsonProperty
    private String oldPassword;
    @JsonProperty
    private String newPassword;
}
