package name.ruhkopf.jaxrs.server.service;

import java.util.List;

import name.ruhkopf.jaxrs.server.model.LoginEntity;



public interface LoginServiceProvider
{
	LoginEntity createLogin(String userToken, String accessToken);

	List<LoginEntity> findAllLogins(int start, int count);

	long countAllLogins();
	
	LoginEntity findLogin(Integer id);

}
