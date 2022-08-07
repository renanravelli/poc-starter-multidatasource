package br.com.renanravelli.pocstartermultidatasource.configurations;

import br.com.renanravelli.pocstartermultidatasource.properties.MultiDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author renanravelli
 */
@Configuration
@EnableConfigurationProperties
public class MultiDataSourcePropertiesConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.primary")
  @ConditionalOnProperty(
      prefix = "spring.datasource.primary",
      name = {"jdbc-url", "username", "password", "driver-class-name", "base-package-repositories"}
  )
  public MultiDataSourceProperties primaryDataSourceProperties() {
    return new MultiDataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.secondary")
  @ConditionalOnProperty(
      prefix = "spring.datasource.secondary",
      name = {"jdbc-url", "username", "password", "driver-class-name", "base-package-repositories"}
  )
  public MultiDataSourceProperties secondaryDataSourceProperties() {
    return new MultiDataSourceProperties();
  }

}
