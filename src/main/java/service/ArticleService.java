package service;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.persistence.EntityManager;

import model.Article;
import model.Brand;
import model.JPAUtil;

public class ArticleService {
	final static String END = "0";

	public static void createArticle() {
		String name;
		int price;
		int brandChoice = -1;
		int quantity = -1;
		String code;
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("If you wanna leave just enter 0.");
			System.out.println("Enter name of article you want to add to your database: ");
			name = sc.nextLine();
			Article article = new Article();
			@SuppressWarnings("unchecked")
			List<Article> articles = em.createQuery("SELECT a FROM Article AS a WHERE a.name= :name")
					.setParameter("name", name).getResultList();
			if (articles.size() > 0) {
				System.out.println("This article already exist!");
				sc.close();
				return;
			}
			article.setName(name);
			System.out.println("Enter price for this article: ");

			try {
				price = sc.nextInt();
			} catch (Exception e) {
				System.out.println("There's an error! Price must be number!");
				e.printStackTrace();
				sc.close();
				return;
			}
			article.setPrice(price);
			@SuppressWarnings("unchecked")
			List<Brand> brands = (List<Brand>) em.createQuery("SELECT b FROM Brand b").getResultList();
			for (Brand brand : brands) {
				System.out.println(brand);
			}
			System.out.println("Choose wich brand is this article: ");
			try {
				brandChoice = sc.nextInt();
			} catch (Exception e1) {
				System.out.println("There's an error! You must enter a number!");
				e1.printStackTrace();
				sc.close();
				return;
			}
			@SuppressWarnings("unchecked")
			List<Brand> brandCheck = (List<Brand>) em
					.createQuery("SELECT b FROM Brand AS b WHERE b.idBrand= :brandChoice")
					.setParameter("brandChoice", brandChoice).getResultList();
			if (brandCheck.isEmpty()) {
				System.out.println("There's an error! Please enter number in front of brands from the list!");
				sc.close();
				return;
			}
			Brand brand = brandCheck.get(0);
			article.setBrand(brand);
			System.out.println("Enter quantity of this article you have on stock: ");
			try {
				quantity = sc.nextInt();
			} catch (Exception e) {
				System.out.println("There's an error! Quantity of article must be number!");
				e.printStackTrace();
				sc.close();
				return;
			}
			article.setQuantity(quantity);
			UUID uuid = UUID.randomUUID();
			code = uuid.toString();
			article.setCode(code);
			em.getTransaction().begin();
			em.persist(article);
			em.getTransaction().commit();
			em.close();
			System.out.println("You have successfully created this article in your database!");
			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an error!");
		}
	}

	public static void ReadArticle() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Article> articles = (List<Article>) em.createQuery("FROM Article").getResultList();
		for (Article article : articles) {
			System.out.println(article);
		}
		em.close();
	}

	public static void updateArticle() {
		Scanner sc = new Scanner(System.in);
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		@SuppressWarnings("unchecked")
		List<Article> articles = (List<Article>) em.createQuery("FROM Article").getResultList();
		for (Article article : articles) {
			System.out.println(article);
		}
		System.out.println("Here's a list of your articles from database. Select one that you want to update.");
		try {
			int articleChoice = sc.nextInt();
			@SuppressWarnings("unchecked")
			List<Article> articleCheck = em.createQuery("SELECT a FROM Article AS a WHERE a.idArticle= :articleChoice")
					.setParameter("articleChoice", articleChoice).getResultList();
			if (articleCheck.isEmpty()) {
				System.out.println("There's an error! You must enter existing number from list of articles!");
				sc.close();
				return;
			}
			Article article = articleCheck.get(0);
			System.out.println("You have chosen " + article.getName() + " successfully!");
			System.out.println("If you want to change it's name enter number 1.");
			System.out.println("If you want to change it's brand enter number 2.");
			System.out.println("If you want to change it's price enter number 3.");
			System.out.println("If you want to change it's quantity enter number 4.");
			int choseNext = -1;
			try {
				choseNext = sc.nextInt();
			} catch (Exception e) {
				System.out.println("There's an error! You must enter a number!");
				e.printStackTrace();
			}
			if (choseNext == 1) {
				System.out.println("Enter new name for " + article.getName() + ": ");
				String newName = sc.next();
				article.setName(newName);
				em.getTransaction().begin();
				em.persist(article);
				em.getTransaction().commit();
				System.out.println("You have successfully changed name of this article!");
				em.close();
				sc.close();
				return;
			}
			if (choseNext == 2) {
				@SuppressWarnings("unchecked")
				List<Brand> brands = (List<Brand>) em.createQuery("FROM Brand").getResultList();
				for (Brand brand : brands) {
					System.out.println(brand);
				}
				System.out.println("Select new brand for article" + article.getName() + "from the list: ");
				int newBrand = sc.nextInt();
				@SuppressWarnings("unchecked")
				List<Brand> brandCheck = em.createQuery("SELECT b FROM Brand AS b WHERE b.idBrand= :newBrand")
						.setParameter("newBrand", newBrand).getResultList();
				if (brandCheck.isEmpty()) {
					System.out.println("There's an error! Enter number infront of brand from list!");
					sc.close();
					return;
				}
				article.setBrand(brandCheck.get(0));
				em.getTransaction().begin();
				em.persist(article);
				em.getTransaction().commit();
				System.out.println("You have successfully changed brand of this article!");
				em.close();
				sc.close();
				return;
			}
			if (choseNext == 3) {
				System.out.println("Enter new price for article " + article.getName() + ": ");
				int newPrice = sc.nextInt();
				article.setPrice(newPrice);
				em.getTransaction().begin();
				em.persist(article);
				em.getTransaction().commit();
				System.out.println("You have successfully changed price of this article!");
				em.close();
				sc.close();
				return;
			}
			if(choseNext == 4) {
				System.out.println("Enter new quantity for article " + article.getName() + ": ");
				int newQuantity = sc.nextInt();
				article.setQuantity(newQuantity);
				em.getTransaction().begin();
				em.persist(article);
				em.getTransaction().commit();
				System.out.println("You have successfully changed quantity of this article!");
				em.close();
				sc.close();
				return;
			}
		} catch (Exception e) {
			System.out.println("There's an error! You must enter a number infront of article you want to update!");
			e.printStackTrace();
		}
	}
	
	public static void deleteArticle() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner (System.in);
		@SuppressWarnings("unchecked")
		List<Article> articles = em.createQuery("FROM Article").getResultList();
		for(Article article : articles) {
			System.out.println(article);
		}
		System.out.println("Select article you want to delete: ");
		int selectForDelete = -1;
		try {
			selectForDelete = sc.nextInt();
		} catch (Exception e) {
			System.out.println("There's an error! You must enter a number!");
			e.printStackTrace();
			sc.close();
			return;
		}
		@SuppressWarnings("unchecked")
		List<Article> articleCheck = em.createQuery("SELECT a FROM Article AS a WHERE a.idArticle= :selectForDelete")
				.setParameter("selectForDelete", selectForDelete).getResultList();
		if(articleCheck.isEmpty()) {
			System.out.println("There's an error! Select existing article from the list!");
			sc.close();
			return;
		}
		Article article = articleCheck.get(0);
		em.getTransaction().begin();
		em.remove(article);
		em.getTransaction().commit();
		System.out.println("You have successfully deleted this article!");
		sc.close();
		em.close();
		return;
	}

	public static void main(String[] args) {
		createArticle();
	}
}
