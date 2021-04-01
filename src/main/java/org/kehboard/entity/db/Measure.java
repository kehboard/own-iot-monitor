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
@Table(name="measure_data")
public class Measure {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="devId")
    private Integer devId;
    @Column(name="unixTimeStamp")
    private Long unixTime;
    @Column(name="measureId")
    private Integer measureNameId;
    @Column(name="data")
    private Float data;
}
