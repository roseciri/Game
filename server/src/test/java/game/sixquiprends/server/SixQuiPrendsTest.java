package game.sixquiprends.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StarterController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets/sixquiprends")
public class SixQuiPrendsTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getUUI() throws Exception {
		this.mockMvc.perform(post("/SixQuiPrends/party"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect((result) -> {
					try {
						UUID.fromString(result.getResponse().getContentAsString());
					} catch (Exception e) {
						throw new AssertionError("Le contenu de la réponse n'est pas un uuid - " + e.getMessage());
					}

				})
				.andDo(document("home"));
	}

	@Test
	public void getUUI2() throws Exception {
		this.mockMvc.perform(post("/SixQuiPrends/party"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect((result) -> {
					try {
						UUID.fromString(result.getResponse().getContentAsString());
					} catch (Exception e) {
						throw new AssertionError("Le contenu de la réponse n'est pas un uuid - " + e.getMessage());
					}

				})
				.andDo(document("home"));
	}
}
