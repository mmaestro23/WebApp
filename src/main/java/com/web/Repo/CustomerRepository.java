package com.web.Repo;

import com.web.Model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    public long countById(Integer id);
}
