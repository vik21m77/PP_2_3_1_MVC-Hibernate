package web.config;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import web.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class AppConfig {

   @Autowired
   private Environment env;

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
      LocalContainerEntityManagerFactoryBean emFactoryBean = new LocalContainerEntityManagerFactoryBean();
      emFactoryBean.setDataSource(getDataSource());

      // Настройка Hibernate как провайдера JPA
      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      emFactoryBean.setJpaVendorAdapter(vendorAdapter);

      // Настройка пакетов с сущностями
      emFactoryBean.setPackagesToScan("web.Model");

      // Hibernate свойства
      Properties jpaProperties = new Properties();
      jpaProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
      jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
      emFactoryBean.setJpaProperties(jpaProperties);

      return emFactoryBean;
   }

   @Bean
   public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory);
      return transactionManager;
   }
}
