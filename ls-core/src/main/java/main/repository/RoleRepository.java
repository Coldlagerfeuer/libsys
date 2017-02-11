package main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.entities.Role;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * @param user
	 * @return
	 */
	Role findByName(String user);

}
