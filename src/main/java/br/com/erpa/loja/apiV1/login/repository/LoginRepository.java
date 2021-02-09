package br.com.erpa.loja.apiV1.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erpa.loja.apiV1.login.model.Login;


public interface LoginRepository extends JpaRepository<Login, Long> {
	
	Optional<Login> findByEmail(String email);

}
