package com.example.managementpharmacy.application.service.impl;

import com.example.managementpharmacy.application.dto.customer.CustomerBodyDto;
import com.example.managementpharmacy.application.dto.customer.CustomerDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSaveDto;
import com.example.managementpharmacy.application.dto.customer.CustomerSmallDto;
import com.example.managementpharmacy.application.mapper.CustomerMapper;
import com.example.managementpharmacy.application.service.CustomerService;
import com.example.managementpharmacy.persistence.entity.Customer;
import com.example.managementpharmacy.persistence.repository.CustomerRepository;
import com.example.managementpharmacy.shared.exception.DataNotFoundException;
import com.example.managementpharmacy.shared.state.enums.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


// Lombok annotation to generate constructors and inject IoC beans.
@RequiredArgsConstructor

// Spring stereotype
@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerSmallDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toSmallDto)
                .toList();
    }

    @Override
    public CustomerDto findById(Long id) throws DataNotFoundException {
        return customerMapper.toDto(getCustomerById(id));
    }

    @Override
    public CustomerSaveDto create(CustomerBodyDto customerBodyDto) {
        validateBirthDate(customerBodyDto.getBirthDate());

        Customer customer = customerMapper.toEntity(customerBodyDto);
        customer.setCreationDate(LocalDate.now());
        customer.setState(State.ENABLED);
        return saveAndMapSaveDto(customer);
    }

    @Override
    public CustomerSaveDto update(Long id, CustomerBodyDto customerBodyDto) throws DataNotFoundException {

        Customer customer = customerMapper.update(getCustomerById(id), customerBodyDto);
        customer.setUpdateDate(LocalDate.now());
        return saveAndMapSaveDto(customer);
    }

    @Override
    public CustomerSaveDto disable(Long id) throws DataNotFoundException {
        Customer customer = getCustomerById(id);
        customer.setState(State.DISABLED);
        return saveAndMapSaveDto(customer);
    }

    @Override
    public List<CustomerSmallDto> findByState(String state) throws DataNotFoundException {
        return customerRepository.findByState(state)
                .stream()
                .map(customerMapper::toSmallDto)
                .toList();
    }

    private Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("Customer not found with id: " + id));
    }


    private CustomerSaveDto saveAndMapSaveDto(Customer customer) {
        return customerMapper.toSaveDto(customerRepository.save(customer));
    }

    private void validateBirthDate(String birthDate) {
        try {
            LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE);

            LocalDate minDate = LocalDate.now().minusYears(18);
            if (!date.isBefore(minDate)) {
                throw new IllegalArgumentException("The customer must be at least 18 years old.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid birth date format. It should follow YYYY-MM-DD.");
        }
    }


}
