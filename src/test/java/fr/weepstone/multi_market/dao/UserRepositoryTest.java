package fr.weepstone.multi_market.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.weepstone.multi_market.MultiMarket;
import fr.weepstone.multi_market.model.NameProvider;
import fr.weepstone.multi_market.model.ParamProvider;
import fr.weepstone.multi_market.model.Provider;
import fr.weepstone.multi_market.model.UserMarket;
import fr.weepstone.multi_market.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MultiMarket.class)
@WebAppConfiguration
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void checkUser() {
		UserMarket result = repository.findByUsername("akemi");
		Assert.assertTrue(result.getId() == 1);
		Assert.assertTrue(result.getUsername().equals("akemi"));
		Assert.assertTrue(result.getPassword().equals("test"));
	}

	@Test
	public void checkProvider() {
		UserMarket result = repository.findByUsername("akemi");
		Assert.assertTrue(result.getProviders().size() == 1);
		Provider provider = result.getProviders().get(0);
		Assert.assertTrue(!provider.getIsFB());
		Assert.assertTrue(provider.getName().equals(NameProvider.FAIT_MAISON));
		Assert.assertTrue(provider.getUser().getUsername().equals("akemi"));
		Assert.assertTrue(provider.getUserName().getKey().equals("user[username]"));
		Assert.assertTrue(provider.getUserName().getValue().equals("akemi"));
		Assert.assertTrue(provider.getPassword().getKey().equals("user[password]"));
		Assert.assertTrue(provider.getPassword().getValue().equals("isbel1710"));
		Assert.assertTrue(provider.getParams().size() == 1);
		ParamProvider param = provider.getParams().get(0);
		Assert.assertTrue(param.getKey().equals("user[remember_me]"));
		Assert.assertTrue(param.getValue().equals("1"));
		Assert.assertTrue(param.getProvider().getId() == 1);
	}

	@Test
	public void createUserTest()
	{
		UserMarket user = new UserMarket();
		user.setUsername("test1");
		user.setPassword("test123");
		repository.save(user);

		UserMarket result = repository.findByUsername("test1");
		Assert.assertTrue(result.getUsername().equals("test1"));
		Assert.assertTrue(result.getPassword().equals("test123"));
	}

	@Test
	public void deleteUserTest()
	{
		repository.delete(repository.findByUsername("test1").getId());
		UserMarket result = repository.findByUsername("test1");
		Assert.assertNull(result);
	}
}
