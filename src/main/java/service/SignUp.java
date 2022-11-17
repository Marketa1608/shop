package service;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import model.JPAUtil;
import model.Role;
import model.User;

public class SignUp {
	final static String END = "0";
	final static int DEFAULT_ROLE = 0;

	public static void signUp() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		try {
			User user = new User();
			System.out.println("If you want to leave just enter 0.");
			System.out.println("Enter your name: ");
			String name = sc.nextLine();
			System.out.println("Enter your lastname: ");
			String lastname = sc.nextLine();
			System.out.println("Enter your email: ");
			String entryEmail = sc.nextLine();
			@SuppressWarnings("unchecked")
			List<Object[]> emailObj = em.createNativeQuery("SELECT * FROM user AS u WHERE u.email = :entry")
					.setParameter("entry", entryEmail).getResultList();
			if (!emailObj.isEmpty()) {
				System.out.println(
						"That email already exist in our database! Enter another one or log in if you already have an account.");
				sc.close();
				return;
			}
			System.out.println("Enter your password: ");
			String entryPw = sc.nextLine();
			System.out.println("Re-enter your password: ");
			String pwCheck = sc.nextLine();
			while (!pwCheck.equals(entryPw)) {
				System.out.println("Your entry is not good! Re-enter your password again!");
				pwCheck = sc.nextLine();
			}
			user.setEmail(entryEmail);
			user.setLastName(lastname);
			user.setName(name);
			user.setPassword(pwCheck);
			Role role = new Role();
			role = (Role) em.createQuery("SELECT r FROM Role AS r WHERE r.idRole = :DEFAULTROLE")
					.setParameter("DEFAULTROLE", DEFAULT_ROLE).getSingleResult();
			user.setRole(role);
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			System.out.println("You have successfully made your account!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an Error!");
		}
		em.close();
		sc.close();
	}

	public static void main(String[] args) {
		signUp();
	}
}
