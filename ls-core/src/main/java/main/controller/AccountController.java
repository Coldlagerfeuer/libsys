package main.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entities.Account;
import main.entities.Role;
import main.repository.AccountRepository;
import main.repository.RoleRepository;

/**
 * @author n.frantzen <nils.frantzen@rwth-aachen.de>
 *
 */
@Controller
@RequestMapping(value = "/user")
public class AccountController {

	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(value = "/register")
	public Account createNewUser(String username, String password) {
		if (accountRepository.findByUsername(username) == null) {
			log.debug("User does not exist, create new - " + username);
		}
		Role userrole = roleRepository.findByName(Role.USER);
		if (userrole == null) {
			roleRepository.save(new Role(Role.USER));
		}

		Set<Role> roles = new HashSet<>();
		roles.add(userrole);

		return accountRepository.save(new Account(username, password, roles));
	}

	public long count() {
		return accountRepository.count();
	}

}