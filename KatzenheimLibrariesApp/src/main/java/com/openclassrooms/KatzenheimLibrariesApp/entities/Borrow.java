package com.openclassrooms.KatzenheimLibrariesApp.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="BORROWS")
@Getter
@Setter
@NoArgsConstructor
public class Borrow implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RETURN_DATE")
	private Date returnDate;
	
	@Column(name="ALREADY_EXTENDED")
	private boolean alreadyExtended;
	
	@Column(name="NUMBER_RELAUNCH")
	private int numberOfRelaunch;

	public Borrow(Date startDate, Date returnDate, boolean alreadyExtended, int numberOfRelaunch) {
		super();
		this.startDate = startDate;
		this.returnDate = returnDate;
		this.alreadyExtended = alreadyExtended;
		this.numberOfRelaunch = numberOfRelaunch;
	}
	
}
