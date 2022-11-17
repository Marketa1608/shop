package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import model.Article;
import model.Cart;
import model.CartHasArticle;
import model.JPAUtil;
import model.User;

public class ShopService {
	@Transactional
	public static void createCart() {
		// napraviti cart
		// user bira proizvode
		// kad zavrsi, pravis cartHasArticle instanci onoliko koliko ima artikala
		// Map<Article, Quantity(INTEGER)>
		// loop kroz mapu -> pravi cart_has_article -> setuj polja, cuvaj
		// ili: -||- ali ne cuvas jedan po jedan, nego napravis listu
		// List<CartHasArticle>, sacuvas celu listu
		// https://www.baeldung.com/jpa-many-to-many
		int userId = Login.login();
		int proceed = 0;
		int choice = -1;
		int quantity = -1;
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		Map<Article, Integer> articlesQuantity = new HashMap();
		Cart cart = new Cart();
		cart.setUserIdUser(userId);
		List<Article> articlesList = new ArrayList<>();
		do {
			try {
				new JPAUtil();

				System.out.println("Welcome to Guru's Shop.");
				System.out.println("Here's a list of product from our shop: ");

				@SuppressWarnings("unchecked")
				List<Article> articles = em.createQuery("FROM Article").getResultList();
				for (Article article : articles) {
					System.out.println(article);
				}
				System.out.println("Enter number of article you chose: ");

				choice = sc.nextInt();
				if(choice == 0) {
					System.out.println("THX AND GOOBYE!");
					break;
				}
				System.out.println("##### " + choice);
				articlesList = em.createQuery("SELECT a FROM Article AS a WHERE a.idArticle = :articleId")
						.setParameter("articleId", choice).getResultList();
				if (!articlesList.isEmpty()) {
					System.out.println("You have selected article: " + articlesList.get(0).getName()
							+ "\nEnter quantity you want to purchace: ");
				} else {
					System.out.println("There's an error! Please chose article from list!");
					
				}
				quantity = sc.nextInt();
				if(quantity == 0) {
					System.out.println("THX AND GOOBYE!");
					break;
				}
				if (articlesList.get(0).getQuantity() == 0) {
					System.out.println("Sorry currently we don't have this article on stock.");
					
				}
				if (quantity <= articlesList.get(0).getQuantity()) {
					System.out.println("You have successfully added " + quantity + "pcs of "
							+ articlesList.get(0).getName() + " to your cart!");
				} else {
					System.out.println(
							"Sorry currently we only have " + articlesList.get(0).getQuantity() + "pcs on stock.");
					
				}

				articlesQuantity.put(articlesList.get(0), quantity);
				
				System.out.println("If you want to pay press 0.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There's an error!");
			}
		} while (choice != 0 || quantity != 0);
	
		LocalDateTime cartTime = LocalDateTime.now();
		cart.setLocalDateTime(cartTime);
		em.getTransaction().begin();
		em.persist(cart);
		em.flush();
		int totalValue = 0;
		for (Map.Entry<Article, Integer> entry : articlesQuantity.entrySet()) {
			Integer q = entry.getValue();
			Article article = entry.getKey();
			CartHasArticle cha = new CartHasArticle();
			cha.setArticle(article);
			cha.setQuantity(q);
			cha.setCart(cart);
			em.persist(cha);
			em.flush();
			totalValue += article.getPrice() * quantity;
		}
		cart.setTotalPrice(totalValue);
		em.persist(cart);
		em.getTransaction().commit();
		
		sc.close();
	}

	public static void main(String[] args) {
		createCart();
	}
}
