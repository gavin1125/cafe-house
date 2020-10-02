package cn.gavin.cafehouse.barista.repository;


import cn.gavin.cafehouse.barista.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
