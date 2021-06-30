package com.openclassrooms.KatzenheimLibrariesApp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.openclassrooms.KatzenheimLibrariesApp.controller.BooksListAndFormController;
import com.openclassrooms.KatzenheimLibrariesApp.service.LibraryUserService;
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	LibraryUserService libraryUserService;
	
	private final Logger logger = LoggerFactory.getLogger(ApplicationSecurityConfig.class);
	
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		logger.info("in ApplicationSecurityConfig in DaoAuthenticationProvider");
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(libraryUserService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("in ApplicationSecurityConfig in configure (AuthenticationManagerBuilder)");
		auth.authenticationProvider(authenticationProvider());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("in ApplicationSecurityConfig in configure (HttpSecurtity http)");
		http
			.csrf().disable()	
			.authorizeRequests()
				.antMatchers(
					"/",
					"/listeDesBibliotheques",
					"/listeDesLivres",
					"/identification",
					"/sInscrire",
					"/identification",
					"/webapp/*",
					"/imageAndLogos/**",
					"/logos/**",
					"/images/**",
					"/myScript.js",
					"/style.css"	
						).permitAll()
				
				.antMatchers(
						"/monCompte",
						"/prolongerUnLivre",
						"/modifierMonAdresseEmail",
						"/modifierMonMotDePasse",
						"/modifierMonCompteInfosPerso",
						"/modifierMonCompte",
						"/emprunterUnLivre"
						).authenticated()
				
				
				.antMatchers(
					"/ajouterUneBibliotheque",
					"/modifierUneBibliotheque",
					"/supprimerUneBibliotheque",
					"/ajouterDesLivres",
					"/modifierUnLivre",
					"/supprimerUnLivre",
					"/ajouterUneImageDeCouvertureAuLivre",
					"/userListForAdmin"
						).hasRole("ADMIN")
				
//				.anyRequest().authenticated()
					
			.and()
			 .formLogin()
			 	.loginPage("/identification")
			 	.defaultSuccessUrl("/monCompte")
			 	
			 	
			 .and()
			  .logout()
			   .invalidateHttpSession(true)
			   .clearAuthentication(true)
			   .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			   .logoutSuccessUrl("/identification?logout")
			   .permitAll();
			   
				;
	}
				
	
	
}
