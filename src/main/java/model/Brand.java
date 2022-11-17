package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the brand database table.
 * 
 */
@Entity
@NamedQuery(name="Brand.findAll", query="SELECT b FROM Brand b")
public class Brand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_brand")
	private int idBrand;

	private String name;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="brand")
	private List<Article> articles;

	//bi-directional many-to-one association to Country
	@ManyToOne
	private Country country;

	public Brand() {
	}

	public int getIdBrand() {
		return this.idBrand;
	}

	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Article addArticle(Article article) {
		getArticles().add(article);
		article.setBrand(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setBrand(null);

		return article;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return idBrand + ")" + name + ", country=" + country;
	}

	
}