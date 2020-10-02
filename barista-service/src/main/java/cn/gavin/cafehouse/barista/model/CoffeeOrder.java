package cn.gavin.cafehouse.barista.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeq")
    @SequenceGenerator(name = "orderSeq", sequenceName = "s_order_seq", allocationSize = 1)
    private Long id;
    private String customer;
    private String waiter;
    private String barista;
    @Enumerated
    @Column(nullable = false)
    private OrderState state;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;

}
