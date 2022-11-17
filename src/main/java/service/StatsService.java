package service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

import javax.persistence.EntityManager;

import model.Article;
import model.JPAUtil;

public class StatsService {
//TODO Koji artikal je najprodavaniji, 
//	kolika je ukupna vrednost artikala, 
//	kolika je vrednost artikala u okviru jendog brenda.
	
	public static Article bestSell() {
		EntityManager em = JPAUtil.getEntityManager();
		Object[] id_article = (Object[]) em.createNativeQuery("SELECT cha.article_id_article, SUM(cha.quantity) as quantity\r\n"
				+ "FROM cart_has_article as cha \r\n"
				+ "GROUP BY cha.article_id_article\r\n"
				+ "ORDER BY quantity DESC LIMIT 1;").getSingleResult();
		return em.find(Article.class, id_article[0]);
		
		
	}
	
	public static int sumAll() {
		EntityManager em = JPAUtil.getEntityManager();
		Integer sumAll =  ((BigDecimal) em.createNativeQuery("SELECT  SUM(article.price*article.quantity) AS sum_all\r\n"
				+ "FROM article\r\n").getSingleResult()).intValue();
		return  sumAll;
	}
	
	public static int sumBrand() {
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		int entry = sc.nextInt();
		Integer sumBrand = ((BigDecimal) em.createNativeQuery("SELECT  SUM(article.price*article.quantity) AS sum_all\r\n"
				+ "FROM article \r\n"
				+ "INNER JOIN brand as b\r\n"
				+ "ON b.id_brand = article.brand_id_brand\r\n"
				+ "WHERE b.id_brand = :idBrand").setParameter("idBrand", entry).getSingleResult()).intValue();
		return sumBrand;
	}
	
	
	public static void main(String[] args) {
		System.out.println(sumBrand());
	}
}
