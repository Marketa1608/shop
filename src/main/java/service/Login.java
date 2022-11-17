package service;

import java.util.Scanner;


import javax.persistence.EntityManager;

import model.JPAUtil;
import model.User;

public class Login {
	final static String END = "0";
	

	public static int login() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		String email;
		String password;
		User user = new User();
		try {
			System.out.println("If you want to leave just enter 0.");
			System.out.println("Enter your email: ");
			email = sc.nextLine();
			if (email.equalsIgnoreCase(END)) {
				sc.close();
				System.exit(0);
			}
			try {
				user = (User) em.createQuery("SELECT u FROM User AS u WHERE u.email = :entry")
						.setParameter("entry", email).getSingleResult();
			} catch (Exception e) {
				System.out.println("User not found!");
				System.exit(0);
			}
			
			if (user == null) {
				System.out.println("1");
				System.out.println("Error! Email doesn't exist. Try again.");
				sc.close();
				System.exit(0);
			}
//			Prevod koji radi hql u pozadini sa nativa!!!
//			User user = new User();
//			user.setIdUser((int)userObj[0]);
//			user.setName(String.valueOf(userObj[1]));
//			user.setLastName(String.valueOf(userObj[2]));
//			user.setEmail(String.valueOf(userObj[3]));
//			user.setPassword(String.valueOf(userObj[4]));
//			Role role = (Role) em.createQuery("select * from role r where r.id = :entry")
//					.setParameter("entry", userObj[5]).getSingleResult();
//			user.setRole(role);
			System.out.println("Enter your password: ");
			password = sc.nextLine();
			if (password.equalsIgnoreCase(END)) {
				sc.close();
				System.exit(0);
			}
			if (!password.equals(user.getPassword())) {
				System.out.println("2");
				System.out.println("Error! Wrong password. Try again.");
				sc.close();
				System.exit(0);
			}
			System.out.println("Your login is successful!");
		} catch (Exception e) {
			System.out.println("There's an error!");
			System.out.println(e);
		}
		
		em.close();
		
		return user.getIdUser();
	}

	public static void main(String[] args) {
		login();
	}
}
