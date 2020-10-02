package cn.gavin.cafehouse.waiter.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "T_ORDER")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class CoffeeOrder extends BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orderSeq")
    @SequenceGenerator(name = "orderSeq",sequenceName = "s_order_seq",allocationSize = 1)
    private Long id;
    private String customer;
    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("id")
    private List<Coffee> items;
    @Column(nullable = false)
    private OrderState state;
    private Integer discount;
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money total;
    private String waiter;
}
