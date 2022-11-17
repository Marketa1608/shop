package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cart_has_article database table.
 * 
 */
@Entity
@Table(name="cart_has_article")
@NamedQuery(name="CartHasArticle.findAll", query="SELECT c FROM CartHasArticle c")
public class CartHasArticle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cha")
	private int idCha;

	private int quantity;

	//bi-directional many-to-one association to Article
	@ManyToOne
	private Article article;

	//bi-directional many-to-one association to Cart
	@ManyToOne
	private Cart cart;

	public CartHasArticle() {
	}

	public int getIdCart() {
		return this.idCha;
	}

	public void setIdCart(int idCha) {
		this.idCha = idCha;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Cart getCart() {
		return this.cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

}