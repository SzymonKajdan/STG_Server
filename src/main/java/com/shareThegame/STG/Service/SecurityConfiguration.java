package com.shareThegame.STG.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    private DataSource dataSource;

    private final String USERS_QUERY ="select email, password, active from user where username=?";
    private final String ROLES_QUERY =" select u.email, r.role from user u inner join user_role ur on(u.id=ur.user_id) inner join role r on(ur.role_id=r.id) where u.email=?";



    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable();
        http.headers().frameOptions().disable ();

        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/h2/**").permitAll();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.authorizeRequests().antMatchers("/client/**").hasAnyAuthority("CLIENT").and ().httpBasic ();
        //user
        http.authorizeRequests().antMatchers("/register").permitAll ();
        http.authorizeRequests().antMatchers("/login").hasAnyAuthority("CLIENT").and ().httpBasic ();
        http.authorizeRequests().antMatchers("/resetPassword").hasAnyAuthority("CLIENT").and ().httpBasic ();
        http.authorizeRequests().antMatchers("/updateProfile").hasAnyAuthority("CLIENT").and ().httpBasic ();
        http.authorizeRequests().antMatchers("/deleteUser").hasAnyAuthority("CLIENT").and ().httpBasic ();

        http.authorizeRequests().antMatchers("getObjecByCity").permitAll();
        http.authorizeRequests().antMatchers("getAllObjects").permitAll();

        http.authorizeRequests().antMatchers("addObject").hasAnyAuthority("CLIENT").and ().httpBasic ();
        http.authorizeRequests ().antMatchers ( "/deleteObject" ).hasAnyAuthority("CLIENT").and ().httpBasic ();
        http.authorizeRequests().antMatchers("getImg").permitAll ();
        http.authorizeRequests().antMatchers("/ReserveHall").hasAnyAuthority("CLIENT").and ().httpBasic ();
        http.authorizeRequests().antMatchers("/ChangeReservation").hasAnyAuthority("CLIENT").and ().httpBasic ();
        http.authorizeRequests().antMatchers("/DeleteReservation").hasAnyAuthority("CLIENT").and ().httpBasic ();

        http.authorizeRequests().antMatchers("/returnTimetableOfSportObject").permitAll();

        http.authorizeRequests ().antMatchers ( "/addMark" ).hasAnyAuthority ( "CLIENT" ).and ().httpBasic ();

    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/h2-console/**");
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }

}