    package com.project.app.securitys.configurationSeccurity;

    import com.project.app.securitys.componentSecurity.corsConfigurationSource;
    import com.project.app.securitys.filterSecurity.JwtTokenFilter;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    import org.springframework.web.filter.CorsFilter;
    import org.springframework.web.servlet.config.annotation.EnableWebMvc;


    import java.util.Arrays;
    import java.util.List;

    import static org.springframework.http.HttpMethod.*;
    import static org.springframework.security.config.Customizer.withDefaults;

    @Configuration
//    @EnableMethodSecurity
    @EnableWebSecurity(debug = true)
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @EnableWebMvc
    @RequiredArgsConstructor
    public class WebSecurityConfig  {
        private final JwtTokenFilter jwtTokenFilter;
    //    private final corsConfigurationSource corsConfigurationSource;
        @Value("${api.prefix}")
        private String apiPrefix;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
            http
//                    .cors(Customizer.withDefaults())
//                    .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                    .authorizeHttpRequests(requests -> {
                        requests
                                .requestMatchers(OPTIONS , "/**").permitAll()

                                .requestMatchers(
                                        String.format("%s/author/register", this.apiPrefix),
                                        String.format("%s/author/login", this.apiPrefix),
                                        String.format("%s/author/getAuthorization", this.apiPrefix)
                                )
                                .permitAll()

                                .requestMatchers(POST,
                                        String.format("%s/dashboard/**", this.apiPrefix),
                                        String.format("%s/post/getPost", this.apiPrefix),
                                        String.format("%s/tag/getTag", this.apiPrefix),
                                        String.format("%s/export/**", this.apiPrefix),
                                        String.format("%s/attchment/filterAttachmentLog", this.apiPrefix)
                                )
                                .permitAll()

                                .requestMatchers(GET,
                                        String.format("%s/export/**", this.apiPrefix),
                                        String.format("%s/attchment/**", this.apiPrefix)
                                )
                                .permitAll()

                                .anyRequest()
                                .authenticated();
                                //.anyRequest().permitAll();
                    })
                    .addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

    //                .cors(cors -> cors.disable())

                    .csrf(AbstractHttpConfigurer::disable)
    //                  .httpBasic(c -> c.disable())
    //                .formLogin(f -> f.disable())
            ;

            http.securityMatcher(String.valueOf(EndpointRequest.toAnyEndpoint()));

    //        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
    //            @Override
    //            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
    //                CorsConfiguration configuration = new CorsConfiguration();
    //                configuration.setAllowedOrigins(List.of("*"));
    //                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    //                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
    //                configuration.setExposedHeaders(List.of("x-auth-token"));
    //                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //                source.registerCorsConfiguration("/**", configuration);
    //                httpSecurityCorsConfigurer.configurationSource(source);
    //            }
    //        });
            return http.build();
        }

//        @Bean
//
//    CorsConfigurationSource corsConfigurationSource() {
//            CorsConfiguration corsConfiguration = new CorsConfiguration();
//            corsConfiguration.setAllowedOrigins(Arrays.asList("*")); // Allow requests from any origin
//            corsConfiguration.setAllowedMethods(Arrays.asList("*")); // Allow all HTTP methods
//            corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
//            corsConfiguration.setExposedHeaders(Arrays.asList("*")); // Expose all headers
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**", corsConfiguration);
//            return source;
//        }

//        @Bean
//        public CorsFilter corsFilter() {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowCredentials(true);
//            config.addAllowedOrigin("*");
//            config.addAllowedHeader("*");
//            config.addAllowedMethod("*");
//            source.registerCorsConfiguration("/**", config);
//            return new CorsFilter(source);
//        }
    }
