package com.openclassrooms.KatzenheimLibrariesApp.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="LIBRARY_USERS", uniqueConstraints = @UniqueConstraint(columnNames="EMAIL"))
@Getter
@Setter
@NoArgsConstructor
public class LibraryUser implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="FIRST_NAME")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String firstName;
	
	@Column(name="LAST_NAME")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String lastName;
	
	@Column(name="EMAIL")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String email;

	@Column(name="ADDRESS")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String address;
	
	@Column(name="PHONE")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String phone;
	
	@Column(name="PASSWORD")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "LIBRARY_USER_ROLES",
			joinColumns = @JoinColumn(
					name = "LIBRARY_USER_ID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn
			(name = "ROLES_ID", referencedColumnName = "ID"))
	private Collection<Role> roles;
	
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
	name = "LIBRARY_USER_BORROWS",
	joinColumns = @JoinColumn(
			name = "LIBRARY_USERS_ID", referencedColumnName = "ID"),
	inverseJoinColumns = @JoinColumn
	(name = "BORROWS_ID", referencedColumnName = "ID"))
	private Collection<Borrow> borrows;



	public LibraryUser(
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String firstName,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String lastName,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String email,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String address,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String phone,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String password,
			Collection<Role> roles, Collection<Borrow> borrows) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.roles = roles;
		this.borrows = borrows;
	}
	
	
	
	
}
