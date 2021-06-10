package com.openclassrooms.KatzenheimLibrariesApp.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="STOCKS")
@Getter
@Setter
@NoArgsConstructor
public class Stock implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="NUMBER_AVAILABLE")
	@Min(value=0, message="doit ête compris entre 1 et 10")
	@Max(value=10, message="doit ête compris entre 1 et 10")
	@NotNull(message="Ce champ ne doit pas être vide")
	private int numberOfCopyAvailable;
	
	@Column(name="NUMBER_OUT")
	@Min(value=0, message="doit ête compris entre 1 et 10")
	@Max(value=10, message="doit ête compris entre 1 et 10")
	@NotNull(message="Ce champ ne doit pas être vide")
	private int numberOfCopyOut;
	
	@Column(name="TOTAL_COPIES")
	@Min(value=0, message="doit ête compris entre 1 et 10")
	@Max(value=10, message="doit ête compris entre 1 et 10")
	@NotNull(message="Ce champ ne doit pas être vide")
	private int totalOfCopy;

	public Stock(
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") int numberOfCopyAvailable,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") int numberOfCopyOut,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") int totalOfCopy) {
		super();
		this.numberOfCopyAvailable = numberOfCopyAvailable;
		this.numberOfCopyOut = numberOfCopyOut;
		this.totalOfCopy = totalOfCopy;
	}
	
	
	
}
