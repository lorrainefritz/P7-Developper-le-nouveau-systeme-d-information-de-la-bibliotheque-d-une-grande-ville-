package com.openclassrooms.KatzenheimLibrariesApp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="ROLES")
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="NAME")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String name;

	public Role(
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String name) {
		super();
		this.name = name;
	}
	
	
	
}
