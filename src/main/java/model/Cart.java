package model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cart database table.
 * 
 */
@Entity
@NamedQuery(name="Cart.findAll", query="SELECT c FROM Cart c")
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cart")
	private Integer idCart;

	@Column(name="date_time")
	private LocalDateTime dateTime;

	@Column(name="total_price")
	private double totalPrice;

	@Column(name="user_id_user")
	private int userIdUser;

	//bi-directional many-to-one association to CartHasArticle
	@OneToMany(mappedBy="cart")
	private List<CartHasArticle> cartHasArticles;

	public Cart() {
	}

	public Integer getIdCart() {
		return this.idCart;
	}

	public void setIdCart(Integer idCart) {
		this.idCart = idCart;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public void setLocalDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getUserIdUser() {
		return this.userIdUser;
	}

	public void setUserIdUser(int userIdUser) {
		this.userIdUser = userIdUser;
	}

	public List<CartHasArticle> getCartHasArticles() {
		return this.cartHasArticles;
	}

	public void setCartHasArticles(List<CartHasArticle> cartHasArticles) {
		this.cartHasArticles = cartHasArticles;
	}

	public CartHasArticle addCartHasArticle(CartHasArticle cartHasArticle) {
		getCartHasArticles().add(cartHasArticle);
		cartHasArticle.setCart(this);

		return cartHasArticle;
	}

	public CartHasArticle removeCartHasArticle(CartHasArticle cartHasArticle) {
		getCartHasArticles().remove(cartHasArticle);
		cartHasArticle.setCart(null);

		return cartHasArticle;
	}

}