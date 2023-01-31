package shop.geeksasang.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionManager;

@Configuration
public class TransactionManagerConfig {

//    @Bean(name = "transactionManager1")
//    public DataSourceTransactionManagerAutoConfiguration jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory);
//        return txManager;
//    }
//
//    @Bean(name = "transactionManager2")
//    public PlatformTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
//        return new MongoTransactionManager(dbFactory);
//    }
//
//
//    @Bean
//    public JtaTransactionManager jtaTransactionManager(
//            @Qualifier("transactionManager1") PlatformTransactionManager mongoTransactionManager,
//            @Qualifier("transactionManager2") PlatformTransactionManager jpaTransactionManager
//    ){
//        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
//        jtaTransactionManager.setTransactionManager();
//
//
//    }
}
