package game.sixquiprends.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectionConfiguration {

	@Bean
	PartiesHolder getParties() {
		return new PartiesHolder();
	}

}
