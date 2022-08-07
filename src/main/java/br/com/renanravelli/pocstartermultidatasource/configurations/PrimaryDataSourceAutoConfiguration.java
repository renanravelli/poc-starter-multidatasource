package br.com.renanravelli.pocstartermultidatasource.configurations;

import br.com.renanravelli.pocstartermultidatasource.annotations.DataSourceAutoConfiguration;
import br.com.renanravelli.pocstartermultidatasource.properties.MultiDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author renanravelli
 */

@DataSourceAutoConfiguration
@EnableJpaRepositories(
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager",
    basePackages = "${spring.datasource.primary.base-package-repositories}"
)
@ConditionalOnProperty(
    prefix = "spring.datasource.primary",
    name = {"jdbc-url", "username", "password", "driver-class-name", "base-package-repositories"}
)
public class PrimaryDataSourceAutoConfiguration {

  @Bean
  public DataSource primaryDataSource(
      @Qualifier("primaryDataSourceProperties") MultiDataSourceProperties properties) {
    return new HikariDataSource(properties);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
      @Qualifier("primaryDataSourceProperties") MultiDataSourceProperties properties,
      @Qualifier("primaryDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {
    return builder.dataSource(dataSource)
        .packages(properties.getBasePackageEntities().toArray(String[]::new))
        .persistenceUnit("primary")
        .build();
  }

  @Bean
  public PlatformTransactionManager primaryTransactionManager(
      @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

}
