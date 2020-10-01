package cn.gavin.cafehouse.waiter.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_COFFEE")
@Builder
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "coffeeSeq")
    @SequenceGenerator(name = "coffeeSeq",sequenceName = "s_coffee_seq",allocationSize = 1)
    private Long id;
    private String name;
    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
}
