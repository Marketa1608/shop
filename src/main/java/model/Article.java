package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the article database table.
 * 
 */
@Entity
@NamedQuery(name="Article.findAll", query="SELECT a FROM Article a")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_article")
	private int idArticle;

	private String code;

	private String name;

	private int price;

	private int quantity;

	//bi-directional many-to-one association to Brand
	@ManyToOne
	private Brand brand;

	//bi-directional many-to-one association to CartHasArticle
	@OneToMany(mappedBy="article")
	private List<CartHasArticle> cartHasArticles;

	public Article() {
	}

	public int getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<CartHasArticle> getCartHasArticles() {
		return this.cartHasArticles;
	}

	public void setCartHasArticles(List<CartHasArticle> cartHasArticles) {
		this.cartHasArticles = cartHasArticles;
	}

	public CartHasArticle addCartHasArticle(CartHasArticle cartHasArticle) {
		getCartHasArticles().add(cartHasArticle);
		cartHasArticle.setArticle(this);

		return cartHasArticle;
	}

	public CartHasArticle removeCartHasArticle(CartHasArticle cartHasArticle) {
		getCartHasArticles().remove(cartHasArticle);
		cartHasArticle.setArticle(null);

		return cartHasArticle;
	}

	@Override
	public String toString() {
		return idArticle + ")" + name + ", price:" + price + ", quantity:" + quantity
				+ ", brand:" + brand.getName();
	}

	
}