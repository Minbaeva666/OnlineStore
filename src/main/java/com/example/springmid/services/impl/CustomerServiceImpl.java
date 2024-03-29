package com.example.springmid.services.impl;

import com.example.springmid.dto.response.CustomerResponseDTO;
import com.example.springmid.dto.reuest.CustomerRequestDTO;
import com.example.springmid.entities.Customer;
import com.example.springmid.exceptions.GeneralException;
import com.example.springmid.mappers.CustomerMapper;
import com.example.springmid.repositories.CustomerRepository;
import com.example.springmid.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO userRequestDTO) {
        if (customerRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new GeneralException("Username already exists");
        }

        if (customerRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new GeneralException("Email already exists");
        }
        Customer user = customerRepository.save(customerMapper.toEntity(userRequestDTO));
        return customerMapper.toDTO(user);
    }

    @Override
    public CustomerResponseDTO update(Long id, CustomerRequestDTO userRequestDTO) {
        Customer user = customerRepository.findById(id).orElseThrow(() -> new GeneralException("User not found exception"));
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        customerRepository.save(user);
        return customerMapper.toDTO(user);
    }

    @Override
    public CustomerResponseDTO get(Long id) {
        return customerMapper.toDTO(customerRepository.findById(id).orElseThrow(() -> new GeneralException("User not found")));
    }

    @Override
    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
