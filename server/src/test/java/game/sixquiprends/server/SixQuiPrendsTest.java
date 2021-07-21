package game.sixquiprends.server;

import com.google.gson.Gson;
import game.sixquiprends.server.controller.StarterController;
import game.sixquiprends.server.model.Party;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
				.andExpect(status().isCreated())
				.andExpect((result) -> {
					try {
						Party party = new Gson().fromJson(result.getResponse().getContentAsString(), Party.class);
						if (party == null) {
							throw new AssertionError("En réponse il doit y avoir une partie");
						}
						UUID.fromString(party.getId());
					} catch (Exception e) {
						throw new AssertionError("Le contenu de la réponse n'est pas un uuid - " + e.getMessage());
					}

				})
				.andDo(document("home"));
	}
}
