package service;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import model.JPAUtil;
import model.Role;
import model.User;

public class AdminService {
	final static String END = "0";
	final static int ADMIN_ROLE = 1;
	final static int CUSTOMER_ROLE = 0;

	public static void addAdmin() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		int userChoice = -1;
		do {
			try {
				System.out.println("If you want to leave just enter 0.");
				@SuppressWarnings("unchecked")
				List<User> userList = (List<User>) em.createQuery("FROM User").getResultList();
				for (User u : userList) {
					System.out.println(u);
				}
				System.out.println("Enter number in front of user that you want to promote to admin.");
				userChoice = sc.nextInt();
				for (User u : userList) {
					if (userChoice == u.getIdUser()) {
						Role role = new Role();
						role = (Role) em.createQuery("SELECT r FROM Role AS r WHERE r.idRole= :ADMINROLE")
								.setParameter("ADMINROLE", ADMIN_ROLE).getSingleResult();
						if (u.getRole() == role) {
							System.out.println("This user already have an admin.");
							sc.close();
							return;
						} else {
							u.setRole(role);
							em.getTransaction().begin();
							em.persist(u);
							em.getTransaction().commit();
							System.out.println("You have successfully changed this user role to admin!");
							sc.close();
							return;
						}
					}
				}
				System.out.println("User id you choose doesn't exist in our base! Please choose someone from list!");
			} catch (Exception e) {
				System.out.println("There's an Error!");
				e.printStackTrace();
			}
		} while (userChoice != 0);
		sc.close();
		em.close();
	}

	public static void RemoveAdmin() {
		new JPAUtil();
		EntityManager em = JPAUtil.getEntityManager();
		Scanner sc = new Scanner(System.in);
		int userChoice = -1;
		do {
			try {
				System.out.println("If you want to leave just enter 0.");
				@SuppressWarnings("unchecked")
				List<User> users = em.createQuery("FROM User").getResultList();
				for (User user : users) {
					System.out.println(user);
				}
				System.out.println("Enter number in front of user that you want to remove admin from.");
				userChoice = sc.nextInt();
				for (User user : users) {
					if (userChoice == user.getIdUser()) {
						Role role = new Role();
						role = (Role) em.createQuery("SELECT r FROM Role AS r WHERE r.idRole= :CUSTOMERROLE")
								.setParameter("CUSTOMERROLE", CUSTOMER_ROLE).getSingleResult();
						if (user.getRole() == role) {
							System.out.println("This user does not have an admin.");
						} else {
							user.setRole(role);
							em.getTransaction().begin();
							em.persist(user);
							em.getTransaction().commit();
							System.out.println("You have successfully removed this users admin role!");
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There's an error!");
			}
		} while (userChoice != 0);
		sc.close();
		em.close();
	}

	public static void main(String[] args) {
		RemoveAdmin();
		;
	}
}
