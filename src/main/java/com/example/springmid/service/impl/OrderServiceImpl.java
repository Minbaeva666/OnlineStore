package com.example.springmid.service.impl;

import com.example.springmid.dto.response.OrderResponseDTO;
import com.example.springmid.dto.request.OrderRequestDTO;
import com.example.springmid.entity.Order;
import com.example.springmid.exception.GeneralException;
import com.example.springmid.mapper.OrderMapper;
import com.example.springmid.repository.OrderRepository;
import com.example.springmid.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDTO create(OrderRequestDTO orderRequestDTO) {
        Order order = orderMapper.toEntity(orderRequestDTO);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Override
    public OrderResponseDTO update(Long id, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new GeneralException("Order not found"));
        order.setOrderDate(orderRequestDTO.getOrderDate());
        order.setProducts(orderRequestDTO.getProducts());
        order.setTotalPrice(orderRequestDTO.getTotalPrice());

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO get(Long id) {
        return orderMapper.toDTO(orderRepository.findById(id).orElseThrow(() -> new GeneralException("Order not found")));
    }

    @Override
    public List<OrderResponseDTO> getAll(Long id) {
        return orderRepository.findAllByUserId(id).stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
