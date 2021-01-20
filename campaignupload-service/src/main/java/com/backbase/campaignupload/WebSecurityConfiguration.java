package com.backbase.campaignupload;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.backbase.buildingblocks.jwt.internal.config.AbstractWebSecurityConfigurerAdapter;
import com.backbase.buildingblocks.security.csrf.CsrfWebSecurityConfigurer;
import com.backbase.buildingblocks.security.csrf.DisableAutoCsrfWebSecurityConfiguration;

@EnableWebSecurity
@DisableAutoCsrfWebSecurityConfiguration
public class WebSecurityConfiguration extends AbstractWebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.apply(CsrfWebSecurityConfigurer.configuration());

		http.csrf().ignoringAntMatchers("/v1/corporate-offers*/**");
		http.csrf().ignoringAntMatchers("/v1/partner-offers*/**");
http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
