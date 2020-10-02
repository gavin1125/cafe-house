package cn.gavin.cafehouse.waiter.service;

import cn.gavin.cafehouse.waiter.integration.Barista;
import cn.gavin.cafehouse.waiter.repository.CoffeeOrderRepository;
import cn.gavin.cafehouse.waiter.support.OrderProperties;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository orderRepository;
    @Autowired
    private OrderProperties orderProperties;
    private String waiterId = UUID.randomUUID().toString();
    @Autowired
    private Barista barista;

    public CoffeeOrder createOrder(String customer, Coffee...coffee) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .discount(orderProperties.getDiscount())
                .total(calcTotal(coffee))
                .items(new ArrayList<>(Arrays.asList(coffee)))
                .waiter(orderProperties.getWaiterPrefix() + waiterId)
                .state(OrderState.INIT)
                .build();
        CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order: {}", saved);
        return saved;
    }

    public CoffeeOrder get(Long id) {
        return orderRepository.getOne(id);
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (order == null) {
            log.warn("Can not find order.");
            return false;
        }
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Updated Order: {}", order);
        if (state == OrderState.PAID) {
            // 有返回值，如果要关注发送结果，则判断返回值
            // 一般消息体不会这么简单
            barista.newOrders().send(MessageBuilder.withPayload(order.getId()).build());
        }
        return true;
    }

    private Money calcTotal(Coffee...coffee) {
        List<Money> items = Stream.of(coffee).map(c -> c.getPrice())
                .collect(Collectors.toList());
        return Money.total(items).multipliedBy(orderProperties.getDiscount())
                .dividedBy(100, RoundingMode.HALF_UP);
    }
}
