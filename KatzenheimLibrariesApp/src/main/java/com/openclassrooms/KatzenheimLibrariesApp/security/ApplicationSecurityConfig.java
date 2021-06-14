package com.openclassrooms.KatzenheimLibrariesApp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()	
			.authorizeRequests()
				.antMatchers(
					"/",
					"listeDesBibliotheques",
					"listeDesLivres",
					//prévoir de passer ça en admin
					"ajouterUneBibliotheque",
					"modifierUneBibliotheque",
					"supprimerUneBibliotheque",
					"ajouterDesLivres",
					"modifierUnLivre",
					"supprimerUnLivre"
					
					).permitAll();
	}

	
	
}
