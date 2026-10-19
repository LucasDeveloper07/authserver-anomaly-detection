package io.github.LucasDeveloper07.authserver.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatasourceConfiguration {

    /*
    * classe de config do datasource
    * injeção dos atributos para config
    * config do hikari datasource
    * */

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean
    public DataSource hikariDatasource() {
        log.info("Iniciando conexão com o banco na url: {}", url);

        HikariConfig hikariConfig = new HikariConfig();

        /*
        * inserção das propriedades do banco na classe config do hikariDatasource
        * */
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driver);

        /*
        * maximumPoolSize -> Máximo de conexões que o pool mantém, contando as conexões em uso e ociosas
        * poolName -> Nome do pool de conexões
        * maxLifetime -> Tempo máximo que uma conexão fica ativa (passada em ms)
        * connectionTimeout -> Tempo de espera para conseguir uma conexão do pool
        * connectionTestQuery -> Query de teste para validar a conexão
        * leakDetectionThreshold -> Alerta se a conexão ficar presa pelo tempo determinado
        * */
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("authserver-db-pool");
        hikariConfig.setMaxLifetime(600000); // 10 minutos
        hikariConfig.setConnectionTimeout(5000);
        hikariConfig.setLeakDetectionThreshold(20000);
        hikariConfig.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(hikariConfig); // Instância do datasource passando a classe de config no construtor
    }
}
