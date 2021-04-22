package org.kehboard.entity.db;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Table(name = "devices")
public class Device {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userId")
    private Integer userId;
    @Column(name = "deviceName")
    private String deviceName;
    @Column(name = "isPublic")
    private Boolean isPublic;
    @Column(name = "longitude")
    private Float longitude;
    @Column(name = "latitude")
    private Float latitude;
    @Column(name = "altitude")
    private Float altitude;
    @Column(name = "apiKey")
    private String apiKey;
    @Column(name = "description")
    private String description;
}
