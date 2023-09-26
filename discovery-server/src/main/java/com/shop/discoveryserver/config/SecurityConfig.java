package com.shop.discoveryserver.config;



//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig  {

//    @Value("${eureka.username}")
//    private String username;
//    @Value("${eureka.password}")
//    private String password;

//    @Bean
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.inMemoryAuthentication()
//                .passwordEncoder(bCryptPasswordEncoder())
//                .withUser(username).password(password)
//                .authorities("USER");
//    }
//    @Bean
//    public void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf(csrf->csrf.disable())
//                .authorizeRequests().anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic(Customizer.withDefaults());
//
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
