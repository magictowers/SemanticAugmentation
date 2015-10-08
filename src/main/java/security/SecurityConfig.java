package security;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LogManager.getLogger(SecurityConfig.class);
	
	@Autowired
	DataSource dataSource;
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {

		Properties storageProperties = new Properties();
		String jdbcDriverClassName;
		String jdbcHost;
		String jdbcDatabase;
		String jdbcSqlUser;
		String jdbcSqlPass;

		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		
		try {
			storageProperties.load(SecurityConfig.class.getClassLoader()
					.getResourceAsStream(
							"at.jku.cis.wisch.paa.storage.properties"));

			jdbcDriverClassName = storageProperties
					.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.driverClassName");
			jdbcHost = storageProperties
					.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.host");
			jdbcDatabase = storageProperties
					.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.database");
			jdbcSqlUser = storageProperties
					.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.username");
			jdbcSqlPass = storageProperties
					.getProperty("at.jku.cis.wisch.paa.storage.mysql.jdbc.password");

			driverManagerDataSource.setDriverClassName(jdbcDriverClassName);
			
			String connectionString = "jdbc:mysql://" + jdbcHost + ":3306/" + jdbcDatabase;
			logger.info("Setting Spring JDBC data source: " + connectionString);
			driverManagerDataSource.setUrl(connectionString);
			
			driverManagerDataSource.setUsername(jdbcSqlUser);
			
			driverManagerDataSource.setPassword(jdbcSqlPass);
			
		} catch (IOException e) {
			logger.error("Error loading properties for Spring JDBC connection.", e);
			e.printStackTrace();
		}

		return driverManagerDataSource;

	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    	// auth.inMemoryAuthentication().withUser("elboato").password("s3cr3t").roles("USER");
    	
    	auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(
				"SELECT username,password,enabled FROM User WHERE User.username=?")
			.authoritiesByUsernameQuery(
				"SELECT User.username,User_Role.role FROM User,User_Role WHERE User.id=User_Role.userid AND User.username=?");
        
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
    	http.csrf().disable(); // TODO there should be another way to enable PUT and POST without disabling CSRF
    	
    	http.authorizeRequests().antMatchers("/downloads/**").permitAll();
        
    	http.authorizeRequests().antMatchers(HttpMethod.GET, "/ui/home").permitAll();
    	http.authorizeRequests().antMatchers(HttpMethod.GET, "/ui/register").permitAll();
    	
    	http.authorizeRequests().antMatchers(HttpMethod.GET, "/ui/myProfile").authenticated();
    	    	
    	http.formLogin();                      
        http.httpBasic();
    	
    	http.authorizeRequests().antMatchers(HttpMethod.PUT, "/track/itemVisit").authenticated();
    	http.authorizeRequests().antMatchers(HttpMethod.POST, "/query/**").authenticated();
    	         
    }
       
}