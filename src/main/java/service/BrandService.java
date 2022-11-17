package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import model.Brand;
import model.Country;
import model.JPAUtil;

public class BrandService {
	final static String END = "0";

	public static void createBrand() {
		String name;
		String countryInput;
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("If you want to leave just enter 0.");
			System.out.println("Enter name of brand you want to add to your database: ");
			name = sc.nextLine();
			Brand brand = new Brand();
			@SuppressWarnings("unchecked")
			List<Brand> brands = (List<Brand>) em.createQuery("SELECT b FROM Brand AS b WHERE b.name= :name")
					.setParameter("name", name).getResultList();
			if (brands.size() > 0) {
				System.out.println("This brand already exist!");
				sc.close();
				return;
			}
			brand.setName(name);
			System.out.println("Enter country of this brand: ");
			countryInput = sc.nextLine();
			Country country = new Country();
			@SuppressWarnings("unchecked")
			ArrayList<Country> countries = (ArrayList<Country>) em
					.createQuery("SELECT c FROM Country AS c WHERE c.name= :name").setParameter("name", countryInput)
					.getResultList();
			if (countries.size() > 0) {
				country = countries.get(0);
			}
			country.setName(countryInput);
			brand.setCountry(country);
			System.out.println("You have successfully made a new Brand in your database!");
			sc.close();
			em.getTransaction().begin();
			em.persist(country);
			em.persist(brand);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an error!");
		}

	}

	public static void updateBrand() {
		int brandChoice = -1;

		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("If you want to leave just enter 0.");
			@SuppressWarnings("unchecked")
			List<Brand> brands = em.createQuery("FROM Brand").getResultList();
			for (Brand b : brands) {
				System.out.println(b);
			}
			System.out.println("Enter number in front of brand you want to modify: ");
			try {
				brandChoice = Integer.parseInt(sc.nextLine());
				if (brandChoice == 0) {
					sc.close();
					return;
				}
			} catch (Exception e) {
				System.out.println("You must enter a number!");
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
			System.out.println("You have accessed " + brand.getName() + " brand.");
			System.out.println("If you want to change name of the brand enter 1.");
			System.out.println("If you want to change country of the brand enter 2.");
			System.out.println("If you want to return enter 0.");
			String newName = "";
			Country country = new Country();
			String newCountry = "";
			String redirect = sc.nextLine();
			boolean updated = false;
			if (redirect.equalsIgnoreCase("1")) {
				System.out.println("Enter new name: ");
				newName = sc.nextLine();
				brand.setName(newName);
				updated = true;

			}
			if (redirect.equalsIgnoreCase("2")) {
				System.out.println("Enter new country");
				newCountry = sc.nextLine();
				@SuppressWarnings("unchecked")
				List<Country> countryCheck = (List<Country>) em
						.createQuery("SELECT c FROM Country AS c WHERE c.name= :newCountry")
						.setParameter("newCountry", newCountry).getResultList();
				if (!countryCheck.isEmpty()) {
					country = countryCheck.get(0);
					brand.setCountry(country);
					updated = true;
				}
				
			}
			if (updated) {
				em.getTransaction().begin();
				em.persist(brand);
				em.getTransaction().commit();
				System.out.println("You have changed this brands name successfully!");
				sc.close();
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("There's an error!");
		}
	}

	public static void readBrand() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		try {
			@SuppressWarnings("unchecked")
			List<Brand> brandList = (List<Brand>) em.createQuery("FROM Brand").getResultList();
			for (Brand brand : brandList) {
				System.out.println(brand);
			}
			System.out.println("Here's a list of all brands from your database.");
		} catch (Exception e) {
			System.out.println("There's an error!");
			return;
		}

	}

	public static void deleteBrand() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("If you want to leave just enter 0.");
			@SuppressWarnings("unchecked")
			List<Brand> brandList = em.createQuery("FROM Brand").getResultList();
			for (Brand brand : brandList) {
				System.out.println(brand);
			}
			System.out.println("Choose from list, wich one you want to delete from your database.");
			String deleteBrand = sc.next();
			if (deleteBrand.equalsIgnoreCase("0")) {
				sc.close();
				return;
			}
			@SuppressWarnings("unchecked")
			List<Brand> brandCheck = em.createQuery("SELECT b FROM Brand as b WHERE b.idBrand = :deleteBrand")
					.setParameter(deleteBrand, "deleteBrand").getResultList();
			if (brandCheck.isEmpty()) {
				System.out.println("There's an error! Select existing brand from the list!");
				sc.close();
				return;
			}
			Brand brand = brandCheck.get(0);
			em.getTransaction().begin();
			em.persist(brand);
			em.getTransaction().commit();
			em.close();
			sc.close();
			System.out.println("You have successfully deleted chosen brand from your database.");
		} catch (Exception e) {
			System.out.println("There's an error!");
		}
	}

	public static void main(String[] args) {
		createBrand();

	}
}
