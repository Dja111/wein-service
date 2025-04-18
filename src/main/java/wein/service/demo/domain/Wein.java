package wein.service.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Wein {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private String weinName;

    @Column
    private String landName;

    @Column
    private String regionName;

    @Column
    private double alkohlGehalt;

    @Column
    private String geschmack;

    @Column
    private double fuellMenge;

    @Column
    private double preis;

    @Column
    private String farbe;

}
