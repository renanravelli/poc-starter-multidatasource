package br.com.renanravelli.pocstartermultidatasource.configurations;

import br.com.renanravelli.pocstartermultidatasource.annotations.DataSourceAutoConfiguration;
import br.com.renanravelli.pocstartermultidatasource.properties.MultiDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author renanravelli
 */

@DataSourceAutoConfiguration
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager",
    basePackages = {"${spring.datasource.secondary.base-package-repositories}"}
)
@ConditionalOnProperty(
    prefix = "spring.datasource.secondary",
    name = {"jdbc-url", "username", "password", "driver-class-name", "base-package-repositories"}
)
public class SecondaryDataSourceAutoConfiguration {

  @Bean
  public HikariDataSource secondaryDataSource(
      @Qualifier("secondaryDataSourceProperties") MultiDataSourceProperties properties) {
    return new HikariDataSource(properties);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
      @Qualifier("secondaryDataSourceProperties") MultiDataSourceProperties properties,
      @Qualifier("secondaryDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder) {
    return builder.dataSource(dataSource)
        .packages(properties.getBasePackageEntities().toArray(String[]::new))
        .persistenceUnit("secondary")
        .build();
  }

  @Bean
  public PlatformTransactionManager secondaryTransactionManager(
      @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
