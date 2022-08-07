package br.com.renanravelli.pocstartermultidatasource.configurations;

import java.util.HashMap;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author renanravelli
 */

@Configuration
public class MainAutoConfiguration {

  @Bean
  public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
    return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
  }

}
