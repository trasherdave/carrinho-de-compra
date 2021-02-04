package com.example.primeirocontrollerspring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuariosDetailsConfig usuariosDetailsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests() //define com as requisições HTTP devem ser tratadas com relação à segurança.
                .antMatchers("/webjars/**").permitAll() //liberar executar bootstrap no /login
                .antMatchers("/cliente/cadastro").permitAll()
                .antMatchers(HttpMethod.POST, "/cliente/save").permitAll()
                .antMatchers("/venda/list").hasAnyRole("ADMIN")
                .anyRequest() //define que a configuração é válida para qualquer requisição.
                .authenticated() //define que o usuário precisa estar autenticado.
                .and()
                .formLogin() //define que a autenticação pode ser feita via formulário de login.
                .loginPage("/login").permitAll() // passamos como parâmetro a URL para acesso à página de login que criamos
                .and()
                .logout() //habilitando o recurso de logout do framework via configuração Java.
                .permitAll(); //indicamos que tudo que estiver relacionado ao logout() pode ser acessado por usuários autenticados ou não.	

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(usuariosDetailsConfig).passwordEncoder(new BCryptPasswordEncoder());

//                .inMemoryAuthentication()
//                .withUser("david").password("$2a$10$mXp.AWjKHIgTb/Om9zGJi.5c7xyEr7LgqliIC23KcysxRi009BfIy").roles("ADMIN")
//                .and()
//                .withUser("teste").password("$2a$10$mXp.AWjKHIgTb/Om9zGJi.5c7xyEr7LgqliIC23KcysxRi009BfIy").roles("EDITOR");
    }

    /**
     * Com o método, instanciamos uma instância do encoder BCrypt e deixando o
     * controle dessa instância como responsabilidade do Spring. Agora, sempre
     * que o Spring Security necessitar disso, ele já terá o que precisa
     * configurado.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
