package com.isazoli.onlinebanking.account;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Stores User information.
 * 
 * @author isazoli
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "usermaster")
@NamedQuery(name = User.FIND_BY_EMAIL, query = "select a from User a where a.email = :email")
public class User implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "User.findByEmail";

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String email;

	@JsonIgnore
	private String password;

	private String role = "ROLE_USER";
	/**
	 * The Bank accounts owned by this user.
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
	private List<BankAccount> bankAccounts;

	protected User() {

	}

	public User(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
}
