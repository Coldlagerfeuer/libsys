package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Account;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
 
	Account findByUsername(String username);
	
}