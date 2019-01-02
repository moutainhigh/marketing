package com.oristartech.marketing.core.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@SuppressWarnings("deprecation")
@Configuration
public class SessionFactoryConfig {
	
	@Bean
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager transManager = new HibernateTransactionManager();
        transManager.setSessionFactory(sf);
        return transManager;
    }
	
	@Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        HibernateJpaSessionFactoryBean sessionFactory=new HibernateJpaSessionFactoryBean();
        return sessionFactory;
    }
	
	@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
	    return hemf.getSessionFactory();  
	}
}
