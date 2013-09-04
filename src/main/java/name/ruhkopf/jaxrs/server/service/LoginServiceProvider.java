package name.ruhkopf.jaxrs.server.service;

import java.util.List;

import name.ruhkopf.jaxrs.server.model.LoginEntity;

import org.springframework.transaction.annotation.Transactional;



public interface LoginServiceProvider
{
	@Transactional
	LoginEntity createLogin(String userToken, String accessToken);

	List<LoginEntity> findAllLogins(int start, int count);

	long countAllLogins();
	
	LoginEntity findLogin(Integer id);

}
