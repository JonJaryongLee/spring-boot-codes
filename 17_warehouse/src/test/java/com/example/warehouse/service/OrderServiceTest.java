package com.example.ordersystem.service;

import com.example.ordersystem.entity.Order;
import com.example.ordersystem.entity.Part;
import com.example.ordersystem.repository.OrderRepository;
import com.example.ordersystem.repository.PartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired private OrderService orderService;

    @Autowired private OrderRepository orderRepository;

    @Autowired private PartRepository partRepository;

    @Test
    void findOrdersTest() {
        // given
        Part part = new Part();
        part.setName("test");
        part.setPrice(1000L);
        Long partId = partRepository.save(part).getId();

        orderService.createOrder(partId);
        orderService.createOrder(partId);

        // when
        List<Order> orders = orderService.findOrders();

        // then
        Assertions.assertThat(orders.size()).isEqualTo(2);
    }

    @Test
    void createOrderTest() {
        // given
        Part part = new Part();
        part.setName("test");
        part.setPrice(1000L);
        Long partId = partRepository.save(part).getId();
        LocalDateTime beforeOrderTime = LocalDateTime.now();
        Order savedOrder = orderService.createOrder(partId);

        // when
        Order foundOrder = orderRepository.findById(savedOrder.getId()).get();

        // then
        Assertions.assertThat(savedOrder.getId()).isEqualTo(foundOrder.getId());
        Assertions.assertThat(savedOrder.getPart().getId()).isEqualTo(foundOrder.getPart().getId());
        Assertions.assertThat(savedOrder.getOrderDate()).isAfterOrEqualTo(foundOrder.getOrderDate());
    }


    @Test
    void createOrderWithInvalidPartIdTest() {
        // given
        Long invalidPartId = 9999L;  // 잘못된 ID

        // when
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(invalidPartId);
        });

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("해당 부품을 찾을 수 없습니다. id=" + invalidPartId);
    }

    @Test
    void deleteOrderTest() {
        // given
        Part part = new Part();
        part.setName("test");
        part.setPrice(1000L);
        Long partId = partRepository.save(part).getId();
        Order order = orderService.createOrder(partId);

        // when
        Long deletedOrderId = orderService.deleteOrder(order.getId());

        // then
        Optional<Order> foundOrder = orderRepository.findById(deletedOrderId);
        Assertions.assertThat(foundOrder).isEqualTo(Optional.empty());
    }

    @Test
    void deleteOrderWithInvalidOrderIdTest() {
        // given
        Long invalidOrderId = 9999L;  // 잘못된 ID

        // when
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteOrder(invalidOrderId);
        });

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("해당 주문을 찾을 수 없습니다. id=" + invalidOrderId);
    }

    @Test
    void calculateTotalOrderPriceTest() {
        // given
        Part part = new Part();
        part.setPrice(1000L);
        Part savedPart = partRepository.save(part);

        orderService.createOrder(savedPart.getId());
        orderService.createOrder(savedPart.getId());

        // when
        Long total = orderService.calculateTotalOrderPrice();

        // then
        Assertions.assertThat(total).isEqualTo(2000L);
    }
}