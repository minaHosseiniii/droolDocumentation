package org.example.repositories.services;

import org.example.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    public List<Customer> getListCustomer() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Maya", "Boomer", "US"));
        customers.add(new Customer("Helen", "James", "UK"));
        customers.add(new Customer("Maya", "Boomer", "UK"));
        return customers;
    }
}
