package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_country")
	private int idCountry;

	private String name;

	//bi-directional many-to-one association to Brand
	@OneToMany(mappedBy="country")
	private List<Brand> brands;

	public Country() {
	}

	public int getIdCountry() {
		return this.idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Brand> getBrands() {
		return this.brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public Brand addBrand(Brand brand) {
		getBrands().add(brand);
		brand.setCountry(this);

		return brand;
	}

	public Brand removeBrand(Brand brand) {
		getBrands().remove(brand);
		brand.setCountry(null);

		return brand;
	}

	@Override
	public String toString() {
		return name;
	}

	
}