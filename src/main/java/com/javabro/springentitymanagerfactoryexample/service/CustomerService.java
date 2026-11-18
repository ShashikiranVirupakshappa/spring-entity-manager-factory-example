package com.javabro.springentitymanagerfactoryexample.service;

import com.javabro.springentitymanagerfactoryexample.model.Customer;
import com.javabro.springentitymanagerfactoryexample.model.CustomerAudit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final EntityManagerFactory entityManagerFactory;

    private final JdbcTemplate jdbcTemplate;

    public CustomerService(EntityManagerFactory entityManagerFactory, JdbcTemplate jdbcTemplate) {
        this.entityManagerFactory = entityManagerFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    /*@PersistenceContext(unitName = "entity_manager_factory_example_db")
    private EntityManager entityManager;*/

    //using entity manager factory or entity manager to get the customer by id
    public Customer getCustomerByIdV1(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = (Session) entityManager.getDelegate();
        if (session == null)
            logger.debug("session is null");
        else
            logger.debug("session object obtained {}",  session);
        try {
            Query query = entityManager.createNativeQuery("select * from customer where id = :id", Customer.class);
            query.setParameter("id", id);
            return (Customer) query.getSingleResult();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    //using jdbc template to get the customer by id
    public Customer getCustomerByIdV2(Long id) {
        String sql = "select * from customer where id = ?";
        List<Customer> customers = new ArrayList<>();
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            logger.debug("getCustomerByIdV2 returns {}",  rowNum);
            Customer customer = new Customer();
            customer.setId(rs.getLong("id"));
            customer.setName(rs.getString("name"));
            customer.setEmail(rs.getString("email"));
            customers.add(customer);
            return customer;
        });
    }

    public List<Customer> getAllCustomers() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<Customer> resultList = entityManager.createNativeQuery("select * from customer", Customer.class).getResultList();
            return resultList;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Customer persistCustomer(Customer customer) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager())  {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            int res = 1/0;
            CustomerAudit customerAudit = new CustomerAudit();
            customerAudit.setCustomer(customer);
            customerAudit.setCreatedOn(LocalDateTime.now());
            customerAudit.setStatus("ACTIVE");
            entityManager.persist(customerAudit);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }
}
