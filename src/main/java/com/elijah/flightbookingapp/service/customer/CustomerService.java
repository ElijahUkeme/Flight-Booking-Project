package com.elijah.flightbookingapp.service.customer;

import com.elijah.flightbookingapp.dto.customer.CustomerDto;
import com.elijah.flightbookingapp.dto.customer.CustomerUpdateDto;
import com.elijah.flightbookingapp.dto.customer.SignInDto;
import com.elijah.flightbookingapp.exception.DataAlreadyExistException;
import com.elijah.flightbookingapp.exception.DataNotFoundException;
import com.elijah.flightbookingapp.model.customer.Customer;
import com.elijah.flightbookingapp.model.trip.TripModel;
import com.elijah.flightbookingapp.repository.customer.CustomerRepository;
import com.elijah.flightbookingapp.repository.trip.TripRepository;
import com.elijah.flightbookingapp.response.ApiResponse;
import com.elijah.flightbookingapp.service.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TripRepository tripRepository;

    public ApiResponse signUpCustomer(CustomerDto customerDto) throws DataAlreadyExistException, NoSuchAlgorithmException {

        if (Objects.nonNull(customerRepository.findByEmail(customerDto.getEmail()))){
            throw new DataAlreadyExistException("Email Address Already Taken By Another Customer");
        }
        if (Objects.nonNull(customerRepository.findByPhoneNumber(customerDto.getPhoneNumber()))){
            throw new DataAlreadyExistException("Phone Number Already been used by another Customer");
        }
        String encryptedCustomerPassword = customerDto.getPassword();
        try {
            encryptedCustomerPassword = hashPassword(customerDto.getPassword());
        }catch (Exception e) {
            e.printStackTrace();
        }
        Customer customer = new Customer();
        customer.setAddress(customerDto.getAddress());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setEmail(customerDto.getEmail());
        customer.setGender(customerDto.getGender());
        customer.setLocalGovernment(customerDto.getLocalGovernment());
        customer.setName(customerDto.getName());
        customer.setApprovedTrip(0);
        customer.setCancelledTrip(0);
        customer.setPendingTrip(0);
        customer.setAge(Period.between(customerDto.getDateOfBirth(),LocalDate.now()).getYears());
        customer.setPassword(encryptedCustomerPassword);
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setStateOfOrigin(customerDto.getStateOfOrigin());
        customer.setProfileImage(customerDto.getProfileImage());
         customerRepository.save(customer);
         return new ApiResponse(true,"Customer Registration Successfully");

    }

    public Customer getCustomerByEmail(String email) throws DataNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (Objects.isNull(customer)){
            throw new DataNotFoundException("There is no customer with this emil address");
        }
        //updateTripCount(customer, customer.getEmail());
        return customer;
    }
    public Customer getCustomerByPhoneNumber(String phoneNumber) throws DataNotFoundException {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
        if (Objects.isNull(customer)){
            throw new DataNotFoundException("There is no customer with this phone Number");
        }
        updateTripCount(customer, customer.getEmail());
        return customer;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toLowerCase().substring(0,12);
        return hash;
    }
    public Customer signInUser(SignInDto signInDto) throws DataNotFoundException, NoSuchAlgorithmException {
        Customer customer = customerRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(customer)){
            throw new DataNotFoundException("Invalid Email or password");
        }
        if (!customer.getPassword().equals(hashPassword(signInDto.getPassword()))){
            throw new DataNotFoundException("Invalid Email or Password");
        }
        updateTripCount(customer, signInDto.getEmail());

        return customer;
    }
    public Customer updateCustomerInformation(CustomerUpdateDto customerUpdateDto, String email) throws DataNotFoundException, NoSuchAlgorithmException {
        Customer customer = customerRepository.findByEmail(email);
        if (Objects.isNull(customer)){
            throw new DataNotFoundException("Invalid Email Address");
        }
        if (Objects.nonNull(customerUpdateDto.getAddress())&& !"".equals(customerUpdateDto.getAddress())){
            customer.setAddress(customerUpdateDto.getAddress());
        }if (Objects.nonNull(customerUpdateDto.getDateOfBirth())&& !"".equals(customerUpdateDto.getDateOfBirth())){
            customer.setDateOfBirth(customerUpdateDto.getDateOfBirth());
            customer.setAge(Period.between(customerUpdateDto.getDateOfBirth(),LocalDate.now()).getYears());
        }if (Objects.nonNull(customerUpdateDto.getEmail())&& !"".equals(customerUpdateDto.getEmail())){
            customer.setEmail(customerUpdateDto.getEmail());
        }if (Objects.nonNull(customerUpdateDto.getName())&& !"".equals(customerUpdateDto.getName())){
            customer.setName(customerUpdateDto.getName());
        }if (Objects.nonNull(customerUpdateDto.getPassword())&& !"".equals(customerUpdateDto.getPassword())){
            customer.setPassword(hashPassword(customerUpdateDto.getPassword()));
        }if (Objects.nonNull(customerUpdateDto.getPhoneNumber())&& !"".equals(customerUpdateDto.getPhoneNumber())){
            customer.setPhoneNumber(customerUpdateDto.getPhoneNumber());
        }
        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers(){
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }
    private void updateTripCount(Customer customer, String customerEmail) throws DataNotFoundException {
        customer = getCustomerByEmail(customerEmail);
        customer.setApprovedTrip(getCustomerApprovedTrip(customerEmail).size());
        customer.setCancelledTrip(getTCustomerCancelledTrip(customerEmail).size());
        customer.setPendingTrip(getCustomerPendingTrip(customerEmail).size());
        customerRepository.save(customer);
    }

    public List<TripModel> getCustomerPendingTrip(String customerEmail) throws DataNotFoundException {
        Customer customer = getCustomerByEmail(customerEmail);
        List<TripModel> customerTrips = tripRepository.findByCustomerWhereStatusIsPending(customer);
        return customerTrips;
    }

    public List<TripModel> getCustomerApprovedTrip(String customerEmail) throws DataNotFoundException {
        Customer customer = getCustomerByEmail(customerEmail);
        List<TripModel> customerTrips = tripRepository.findByCustomerWhereStatusIsApproved(customer);
        return customerTrips;
    }

    public List<TripModel> getTCustomerCancelledTrip(String customerEmail) throws DataNotFoundException {
        Customer customer = getCustomerByEmail(customerEmail);
        List<TripModel> customerTrips = tripRepository.findByCustomerWhereStatusIsCancelled(customer);
        return customerTrips;
    }

}
