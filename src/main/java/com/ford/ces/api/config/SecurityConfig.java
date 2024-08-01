package com.ford.ces.api.config;

import com.ford.ces.api.util.ProfilingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import java.util.Arrays;

import static org.springframework.security.oauth2.jwt.JwtClaimNames.AUD;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig  {

	@Configuration
	@Order(10)
	public static class ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		ProfilingProperties profilingProperties;

		@Autowired
		JwtDecoder jwtDecoder;

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			JwtDecoder newJwtDecoder = wrapJwtDecoderWithAudienceCheck(this.jwtDecoder, profilingProperties.getAudience());
			http
					.cors()
					.and()
					.antMatcher("/fafcces/**")
					.authorizeRequests()
					.antMatchers("/swagger-ui/**","/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs*",
							"/actuator/**").permitAll()
					.antMatchers("/health").permitAll()
					.anyRequest().authenticated()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.oauth2ResourceServer()
					.jwt()
					.decoder(newJwtDecoder)
			;
		}

	}

	// helper
	static JwtDecoder wrapJwtDecoderWithAudienceCheck(JwtDecoder jwtDecoder, String audience) {
		return (token) -> {
			Jwt jwt = jwtDecoder.decode(token);
			if (jwt.containsClaim(AUD) && !jwt.getClaimAsStringList(AUD).contains(audience)) {
				throw new JwtValidationException("Audience field does not match: " + audience, Arrays.asList(new OAuth2Error("invalid_aud")));
			}
			return jwt;
		};
	}
}