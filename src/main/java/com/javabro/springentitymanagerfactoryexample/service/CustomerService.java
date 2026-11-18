package com.javabro.springentitymanagerfactoryexample.service;

import com.javabro.springentitymanagerfactoryexample.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {


    private EntityManagerFactory entityManagerFactory;

    private JdbcTemplate jdbcTemplate;

    public CustomerService(EntityManagerFactory entityManagerFactory, JdbcTemplate jdbcTemplate) {
        this.entityManagerFactory = entityManagerFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    /*@PersistenceContext(unitName = "entity_manager_factory_example_db")
    private EntityManager entityManager;*/

    //using entity manager factory or entity manager to get the customer by id
    public Customer getCustomerByIdV1(Long id) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
        Query query = entityManager.createNativeQuery("select * from customer where id = :id", Customer.class);
        query.setParameter("id", id);
        return (Customer) query.getSingleResult();
    } catch (RuntimeException e) {
        throw new RuntimeException(e);
    }finally {
        entityManager.close();
    }
    }

    //using jdbc template to get the customer by id
    public Customer getCustomerByIdV2(Long id) {
        String sql = "select * from customer where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Customer.class);
    }
}
