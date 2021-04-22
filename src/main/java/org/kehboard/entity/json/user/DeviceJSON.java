package org.kehboard.entity.json.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder

public class DeviceJSON {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private Integer userId;
    @JsonProperty
    private String deviceName;
    @JsonProperty
    private Boolean isPublic;
    @JsonProperty
    private Float longitude;
    @JsonProperty
    private Float latitude;
    @JsonProperty
    private Float altitude;
    @JsonProperty
    private String apiKey;
    @JsonProperty
    private String description;
}
