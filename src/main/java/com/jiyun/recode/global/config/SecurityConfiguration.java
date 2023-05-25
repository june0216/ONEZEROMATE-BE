package com.jiyun.recode.global.config;

import com.jiyun.recode.global.jwt.JwtEntryPoint;
import com.jiyun.recode.global.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // preAuthorize 활성화
@EnableWebSecurity  // Spring Security 설정 활성화
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtEntryPoint jwtEntryPoint;
	//private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtFilter jwtFilter;
	//private final CustomOAuth2UserService customOAuth2UserService;
	//private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	// Spring Security에서 제공하는 비밀번호 암호화 클래스.
	// Service에서 사용할 수 있도록 Bean으로 등록한다.
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // Non-Browser Clients만을 위한 API 서버이므로, csrf 보호기능 해제
				.headers().frameOptions().sameOrigin() // h2-console 화면을 보기 위한 처리.

				.and()
				.authorizeRequests() // URL 별로 리소스에 대한 접근 권한 관리
				.antMatchers("/*").permitAll() // 모든 접근 가능
				.anyRequest().permitAll()// 우선 다 허용
				.and()
				.cors().configurationSource(corsConfigurationSource()) // CorsConfigurationSource 를 cors 정책의 설정파일 등록


				.and() // 시큐리티는 기본적으로 세션을 사용하지만, 우리는 세션을 사용하지 않기 때문에 Stateless로 설정
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
				// JwtFilter를 인증을 처리하는 UsernamePasswordAuthenticationFilter 전에 추가한다.
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

				.exceptionHandling() // 예외 처리 기능 작동
				.authenticationEntryPoint(jwtEntryPoint); // 인증 실패시 처리
				//.accessDeniedHandler(jwtAccessDeniedHandler); // 인가 실패시 처리

		return http.build();
	}

	// CORS 허용 적용
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedOrigin("http://localhost:5500");
		configuration.addAllowedOrigin("http://localhost:3000");
		configuration.setAllowCredentials(true);
		configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
