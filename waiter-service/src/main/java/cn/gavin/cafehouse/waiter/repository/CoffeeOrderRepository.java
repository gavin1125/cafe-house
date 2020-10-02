package cn.gavin.cafehouse.waiter.repository;

import cn.gavin.cafehouse.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {

}
