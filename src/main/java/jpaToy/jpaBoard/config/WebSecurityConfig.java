package jpaToy.jpaBoard.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource; //application.yml datasource

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/","/account/register" ,"/api/**" , "/h2-console/**", "/boards/**").permitAll() //이 url은 누구나 접근할 수 있다(로그인없이). h2 별도 설정
                    .anyRequest().authenticated() // 위를 제외한 나머지 요청들은 로그인 해야만한다.
                    .and()
                .formLogin()
                    .loginPage("/account/login") // 로그인 안 됐을 때, 이 페이지로 이동한다.
                        .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder()) // passwordEncoder
                .usersByUsernameQuery("select username, password, enabled " + //인증처리 쿼리 추가
                        "from user " +
                        "where username = ?")
                .authoritiesByUsernameQuery("select u.username, r.role_type " + //권한 부여 쿼리 추가
                        "from user_role ur inner join user u on ur.user_id = u.user_id " +
                        "inner join role r on ur.role_id = r.id " +
                        "where u.username = ?");
    }
    //Authentication 로그인
    //Authorization 권한

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //BeanCurrentlyInCreationExceptin 오류 =>inner class는 static 클래스로 해야됨
    }


}