package game.sixquiprends.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import game.sixquiprends.server.controller.StarterController;
import game.sixquiprends.server.model.Party;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

	@Test
	public void startGame() throws Exception {
		String contentAsString = this.mockMvc.perform(post("/SixQuiPrends/party")).andReturn().getResponse().getContentAsString();
		Party party = new Gson().fromJson(contentAsString, Party.class);
		this.mockMvc.perform(post("/SixQuiPrends/party/{partyId}/player", party.getId()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson("TOTO"))).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(post("/SixQuiPrends/party/{partyId}/player", party.getId()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson("TITI"))).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(post("/SixQuiPrends/party/{partyId}/start", party.getId())).andDo(print()).andExpect(status().isOk());
	}

	class Player {
		String name;

		public Player(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
