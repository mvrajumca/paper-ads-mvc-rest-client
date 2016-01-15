package com.acme.ads.controller.it;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Raju Merugumala
 * 
 
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = { "classpath:mvc-dispatcher-servlet.xml" })
*/
@Ignore
public class AcmeAdsControllerTest {
	
	/**
	 * Integration Tests for ACMEAdsManagementSystem.
	 */

	@Autowired
	WebApplicationContext webContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext)
				.build();
	}

	/**
	 * Test scenario to validate the configured newspaper exist or not in the database.
	 * For New TestData it should return as not exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsNewsPaperExist01() throws Exception {
		mockMvc.perform(
				post("/findnewspaper")
						.param("paperName", TestData.NEWS_PAPER_NAME)
						.param("newsContactNbr",
								TestData.NEWS_PAPER_CONTACT_NBR)
						.accept(MediaType.ALL)).andExpect(status().isOk())
				.andExpect(model().attribute("isExist", false));
	}

	/**
	 * Test Scenario to create newspaper.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateNewsPaper() throws Exception {
		mockMvc.perform(
				post("/createpaper")
						.param("paperName", TestData.NEWS_PAPER_NAME)
						.param("newsContactNbr",
								TestData.NEWS_PAPER_CONTACT_NBR)
						.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("resultMsg",
								TestData.NEWSPAPER_RESULT_SUCCESS_MSG));
	}

	/**
	 * Test scenario to validate the configured newspaper exist or not in the database.
	 * For the configured TestData it should return as exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsNewsPaperExist02() throws Exception {
		mockMvc.perform(
				post("/findnewspaper")
						.param("paperName", TestData.NEWS_PAPER_NAME)
						.param("newsContactNbr",
								TestData.NEWS_PAPER_CONTACT_NBR)
						.accept(MediaType.ALL)).andExpect(status().isOk())
				.andExpect(model().attribute("isExist", true));
	}

	/**
	 * Test scenario to validate the creation of same newspaper.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateExistingNewsPaper() throws Exception {
		mockMvc.perform(
				post("/createpaper")
						.param("paperName", TestData.NEWS_PAPER_NAME)
						.param("newsContactNbr",
								TestData.NEWS_PAPER_CONTACT_NBR)
						.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("resultMsg",
								TestData.NEWSPAPER_RESULT_DUP_MSG));
	}

	/**
	 * Test scenario to validate the configured advertisement exist or not in the database.
	 * For New TestData it should return as not exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsAdvtExist01() throws Exception {
		mockMvc.perform(
				post("/findadvertisement")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("adContactNbr",
								TestData.ADVERTISEMENT_CONTACT_NBR)
						.param("adDetails", TestData.ADVERTISEMENT_DETAILS)
						.accept(MediaType.ALL)).andExpect(status().isOk())
				.andExpect(model().attribute("isExist", false));
	}

	/**
	 * Test Scenario to create advertisement.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateAdvertisement() throws Exception {
		mockMvc.perform(
				post("/createad")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("adContactNbr",
								TestData.ADVERTISEMENT_CONTACT_NBR)
						.param("adDetails", TestData.ADVERTISEMENT_DETAILS)
						.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("resultMsg",
								TestData.ADVERTISEMENT_RESULT_SUCCESS_MSG));
	}

	/**
	 * Test scenario to validate the configured advertisement exist or not in the database.
	 * For the configured TestData it should return as exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsAdvtExist02() throws Exception {
		mockMvc.perform(
				post("/findadvertisement")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("adContactNbr",
								TestData.ADVERTISEMENT_CONTACT_NBR)
						.param("adDetails", TestData.ADVERTISEMENT_DETAILS)
						.accept(MediaType.ALL)).andExpect(status().isOk())
				.andExpect(model().attribute("isExist", true));
	}

	/**
	 * Test scenario to validate the creation of same advertisement.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateExistingAdvertisement() throws Exception {
		mockMvc.perform(
				post("/createad")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("adContactNbr",
								TestData.ADVERTISEMENT_CONTACT_NBR)
						.param("adDetails", TestData.ADVERTISEMENT_DETAILS)
						.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("resultMsg",
								TestData.ADVERTISEMENT_RESULT_DUP_MSG));
	}
	/**
	 * Test scenario to validate the advertisement published in the newspaper.
	 * For the configured TestData it should return as not exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsAdvtExistInNewsPaper01() throws Exception {
		mockMvc.perform(
				post("/findnewsadvertisement")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("paperName",
								TestData.NEWS_PAPER_NAME)
						.accept(MediaType.ALL)).andExpect(status().isOk())
				.andExpect(model().attribute("isExist", false));
	}
	/**
	 * Test scenario to publish the advertisements in the newspaper.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreatenewsAds() throws Exception {
		mockMvc.perform(
				post("/createnewsadswithnames")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("paperName",
								TestData.NEWS_PAPER_NAME)
						.accept(MediaType.ALL))
				.andExpect(model().attribute("resultMsg", TestData.NEWS_ADV_RESULT_MSG));
	}
	/**
	 * Test scenario to validate the advertisement published in the newspaper.
	 * For the configured TestData it should return as exist.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIsAdvtExistInNewsPaper02() throws Exception {
		mockMvc.perform(
				post("/findnewsadvertisement")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("paperName",
								TestData.NEWS_PAPER_NAME)
						.accept(MediaType.ALL)).andExpect(status().isOk())
				.andExpect(model().attribute("isExist", true));
	}
	/**
	 * Test scenario to validate publishing the same advertisement in the newspaper.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateExistingNewsAds() throws Exception {
		mockMvc.perform(
				post("/createnewsadswithnames")
						.param("advertisementName", TestData.ADVERTISEMENT_NAME)
						.param("paperName",
								TestData.NEWS_PAPER_NAME)
						.accept(MediaType.ALL))
				.andExpect(model().attribute("resultMsg", TestData.NEWS_ADV_RESULT_DUP_MSG));
	}
}
