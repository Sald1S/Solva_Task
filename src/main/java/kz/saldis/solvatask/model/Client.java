package kz.saldis.solvatask.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client extends BaseEntity{

    private String firstName;
    private String lastName;
    private int iin;
    @OneToMany
    private List<MonthlyLimitGoods> limitGoods;
    @OneToMany
    private List<MonthlyLimitServices> limitServices;

}
