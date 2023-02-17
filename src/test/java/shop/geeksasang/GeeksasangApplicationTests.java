package shop.geeksasang;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shop.geeksasang.controller.applelogin.util.AppleUtils;

//TODO: @SpringBootTest 주석처리 안하면 github action 으로 빌드할 때 테스트 에러남
@SpringBootTest
class GeeksasangApplicationTests {

	@Autowired
	AppleUtils appleUtils;

	@Test
	void contextLoads() {
		String applePublicAuthUrl = appleUtils.getApplePublicAuthUrl();
		System.out.println("applePublicAuthUrl = " + applePublicAuthUrl);
	}

}
