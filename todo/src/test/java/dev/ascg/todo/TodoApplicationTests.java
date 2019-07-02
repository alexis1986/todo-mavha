package dev.ascg.todo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
	
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TodoApplication.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Test
	public void a_create_thenReturnJson() throws Exception {
		URL url = new URL("https://i.imgur.com/OvMZBs9.jpg");
		BufferedImage bufferedImage = ImageIO.read(url);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", baos );
		baos.flush();
		
		MockMultipartFile image = new MockMultipartFile("image", "OvMZBs9.jpg", "image/jpg", baos.toByteArray());
		
	    mvc.perform(MockMvcRequestBuilders.multipart("/todo").file(image).param("description", "tarea 1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is("tarea 1")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("PENDIENTE")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.image", CoreMatchers.endsWith("OvMZBs9.jpg")));;
	    
	    baos.close();
	}
	
	@Test
	public void b_get_by_description_thenReturnJsonArray() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/todo").param("description", "tarea 1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)));
	}
	
	@Test
	public void c_patch_resolve_thenReturnJson() throws Exception {
		mvc.perform(MockMvcRequestBuilders.patch("/todo/1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is("RESUELTA")));
	}

}
