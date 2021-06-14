package com.openclassrooms.KatzenheimLibrariesApp.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="BOOKS")
@Getter
@Setter
@NoArgsConstructor
public class Book {



	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="TITLE")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String title;
	
	@Column(name="AUTHOR")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String author;
	
	@Column(name="TYPE")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String type;
	
	@Column(name="SUMMARY")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String summary;
	
	
	@Column(name="PUBLISHER")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String publisher;
	
	@Column(name="LANGUAGE")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String language;
	
	@Column(name="SHELVE")
	@Size(max=65, message="65 charactères maximum")
	@NotBlank(message="Ce champ ne doit pas être vide")
	private String shelve;
	
	
	
	@Column(name="CREATION_DATE")
	private String creationDate;
	
	@Lob
	@Column (name = "COVER", length= Integer.MAX_VALUE, nullable= true)
	private String cover;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "BOOK_LIBRARY",
			joinColumns = @JoinColumn(
					name = "BOOKS_ID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn
			(name = "LIBRARIES_ID", referencedColumnName = "ID"))
	private Library library;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "BOOK_STOCK",
			joinColumns = @JoinColumn(
					name = "BOOKS_ID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn
			(name = "STOCKS_ID", referencedColumnName = "ID"))
	private Stock stock;

	
	public Book(
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String title,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String author,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String summary,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String publisher,
			@Size(max = 65, message = "65 charactères maximum") @NotBlank(message = "Ce champ ne doit pas être vide") String language,
			String creationDate, String cover, Library library, Stock stock) {
		super();
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.publisher = publisher;
		this.language = language;
		this.creationDate = creationDate;
		this.cover = cover;
		this.library = library;
		this.stock = stock;
	}

	
	
}
