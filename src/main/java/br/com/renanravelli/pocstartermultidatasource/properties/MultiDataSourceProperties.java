package br.com.renanravelli.pocstartermultidatasource.properties;

import com.zaxxer.hikari.HikariConfig;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * @author renanravelli
 */

@Getter
@Setter
@NoArgsConstructor
@Validated
public class MultiDataSourceProperties extends HikariConfig {

  @NotNull
  private String basePackageRepositories;

  @NotNull
  private List<@NotEmpty String> basePackageEntities;

}
