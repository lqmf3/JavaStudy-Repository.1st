package raisetech.StudentManagement.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("raisetech.StudentManagement.repository")  // Mapperインターフェースがあるパッケージ
public class MyBatisConfig {

  // DataSourceのBean定義
  @Bean
  public DataSource dataSource() {
    // HikariCPの設定をここで行います
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3308/StudentManagement");  // DBのURL
    dataSource.setUsername("root");  // DBのユーザー名
    dataSource.setPassword("AkaikeOike1112");  // DBのパスワード
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");  // JDBCドライバ
    return dataSource;
  }



  // SqlSessionFactoryのBean定義
  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

    // DataSourceの設定
    sessionFactoryBean.setDataSource(dataSource);

    // Mapper XMLファイルの場所を指定
    sessionFactoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml")
    );

    // SqlSessionFactoryを返す
    return sessionFactoryBean.getObject();
  }


  // SqlSessionTemplateのBean定義
  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}

