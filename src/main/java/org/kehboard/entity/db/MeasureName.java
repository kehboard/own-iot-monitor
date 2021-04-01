package org.kehboard.entity.db;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Entity
@Table(name="measurements")
public class MeasureName {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String measureName;
    @Column(name="symbol")
    private String measureSymbol;
    @Column(name="description")
    private String description;
    @Column(name = "iot_name")
    private String iotName;
    @Column(name = "devId")
    private Integer devId;
    @Column(name = "userId")
    private Integer userId;

}
