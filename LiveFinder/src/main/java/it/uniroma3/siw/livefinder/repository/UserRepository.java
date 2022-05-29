package it.uniroma3.siw.livefinder.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
