package com.acme.ads.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.acme.ads.model.Advertisement;
import com.acme.ads.model.NewsAds;
import com.acme.ads.model.NewsPaper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * This class is a controller that accepts the client request and makes a REST
 * call to process the request.
 * 
 * @author Raju
 */
@Controller
@RequestMapping("/")
public class AcmeAdsController {

	/**
	 * Home page will be constructed by using the below resource.
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(ModelMap model) {
		model.addAttribute("message", "Welcome Admin");
		return "home";
	}

	/**
	 * Create NewsPaper home page will be generated from this resource.
	 * 
	 * @Param model
	 * @return String
	 */
	@RequestMapping(value = "/createnewspaper", method = RequestMethod.GET)
	public String createNewsPaperHome(ModelMap model) {
		model.addAttribute("message", "Welcome Admin");
		return "NewsPaper";
	}

	/**
	 * This resouce captures the NewsPaper information from UI and creates the
	 * newspaper.
	 * 
	 * @param model
	 * @param paperName
	 * @param contactNbr
	 * @return String
	 */
	@RequestMapping(value = "/createpaper", method = RequestMethod.POST)
	public String createNewsPaper(ModelMap model,
			@RequestParam("paperName") String paperName,
			@RequestParam("newsContactNbr") String contactNbr) {
		model.addAttribute("message", "Welcome Admin");
		NewsPaper newsPaper = new NewsPaper();
		newsPaper.setNewsPaperName(paperName);
		newsPaper.setContactNbr(contactNbr);
		WebResource webResource = getWebResource("createnewspaper");
		// boolean isDupRecord = false;
		ClientResponse response = webResource.type("application/json")
				.accept("application/json")
				.post(ClientResponse.class, newsPaper);
		if (response.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to create News Paper : HTTP error code : "
							+ response.getStatus());
		}
		int result = response.getEntity(Integer.class);
		if (result == 2) {
			model.addAttribute("resultMsg", "NewsPaper already exist...");
		} else {
			model.addAttribute("resultMsg", "NewsPaper created successfully...");
		}
		return "NewsPaper";
	}

	/**
	 * This resource generates the Advertisement home page.
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/createadvertisement", method = RequestMethod.GET)
	public String createAdvertisementHome(ModelMap model) {
		model.addAttribute("message", "Welcome Admin");
		return "Advertisement";
	}

	/**
	 * This resource captures the Advertisement details from the UI and creates
	 * the advertisement.
	 * 
	 * @param model
	 * @param advertisementName
	 * @param advContactNbr
	 * @param advDetails
	 * @return String
	 */
	@RequestMapping(value = "/createad", method = RequestMethod.POST)
	public String createAdvertisement(ModelMap model,
			@RequestParam("advertisementName") String advertisementName,
			@RequestParam("adContactNbr") String advContactNbr,
			@RequestParam("adDetails") String advDetails) {
		model.addAttribute("message", "Welcome Admin");
		Advertisement advertisement = new Advertisement();
		advertisement.setAdvContactNbr(advContactNbr);
		advertisement.setAdvDetails(advDetails);
		advertisement.setAdvName(advertisementName);
		WebResource webResource = getWebResource("createadvertisement");
		ClientResponse response = webResource.type("application/json").post(
				ClientResponse.class, advertisement);
		if (response.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to create Advertisement : HTTP error code : "
							+ response.getStatus());
		}

		int result = response.getEntity(Integer.class);
		if (result == 2) {
			model.addAttribute("resultMsg", "Advertisement already exist...");
		} else {
			model.addAttribute("resultMsg",
					"Advertisement created successfully...");
		}
		return "Advertisement";
	}

	/**
	 * This resouce generates the NewsAdvertisements home page.
	 * 
	 * @param model
	 * @return String
	 */
	// API Orchestration scenario
	@RequestMapping(value = "/createnewsad", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String createNewsAdvertisementHome(ModelMap model) {
		model.addAttribute("message", "Welcome Admin");
		WebResource newsPapaersResource = getWebResource("newspapers");
		ClientResponse newsPapersClient = newsPapaersResource.type(
				"application/json").get(ClientResponse.class);
		if (newsPapersClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ newsPapersClient.getStatus());
		}
		List<NewsPaper> listNews = newsPapersClient
				.getEntity(new GenericType<List<NewsPaper>>() {
				});

		WebResource adsResource = getWebResource("advertisements");
		ClientResponse adsClient = adsResource.type("application/json").get(
				ClientResponse.class);
		if (adsClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ adsClient.getStatus());
		}
		List<Advertisement> listAds = adsClient
				.getEntity(new GenericType<List<Advertisement>>() {
				});

		model.addAttribute("newspapers", listNews);
		model.addAttribute("advertisements", listAds);

		return "NewsAd";
	}

	/**
	 * This resource creates the advertisements and published newspaper
	 * information.
	 * 
	 * @param model
	 * @param newsId
	 * @param advId
	 * @return String
	 */
	@RequestMapping(value = "/createnewspaperadvertisement", method = RequestMethod.POST)
	public String createNewsPaperAdvertisement(ModelMap model,
			@RequestParam("newspaper") String newsId,
			@RequestParam("advertisement") String advId) {
		model.addAttribute("message", "Welcome Admin");
		NewsAds newsAd = new NewsAds();
		newsAd.setAdvId(Integer.parseInt(advId));
		newsAd.setNewsPaperId(Integer.parseInt(newsId));
		WebResource webResource = getWebResource("createnewspaperadvertisment");
		ClientResponse response = webResource.type("application/json").post(
				ClientResponse.class, newsAd);
		if (response.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to create NewsAdvertisement : HTTP error code : "
							+ response.getStatus());
		}
		int result = response.getEntity(Integer.class);
		if (result == 2) {
			model.addAttribute("resultMsg",
					"NewsPaperAdvertisement already exist...");
		} else {
			model.addAttribute("resultMsg",
					"NewsPaperAdvertisement created successfully...");
		}

		return "redirect:/createnewsad.htm";
	}

	// API Orchestration scenario
	/**
	 * This resource generates the report for the advertisement and
	 * corresponding newspaper.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewads", method = RequestMethod.GET)
	public String viewAdvertisementsHome(ModelMap model) {
		model.addAttribute("message", "Welcome Admin");
		// Get NewsAdvertisements
		WebResource newsPapaerAdsResource = getWebResource("newspapersads");
		ClientResponse newsPaperAdsClient = newsPapaerAdsResource.type(
				"application/json").get(ClientResponse.class);
		if (newsPaperAdsClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ newsPaperAdsClient.getStatus());
		}
		List<NewsAds> listNewsAds = newsPaperAdsClient
				.getEntity(new GenericType<List<NewsAds>>() {
				});
		// Get NewsPapers
		WebResource newsPapaersResource = getWebResource("newspapers");
		ClientResponse newsPapersClient = newsPapaersResource.type(
				"application/json").get(ClientResponse.class);
		if (newsPapersClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ newsPapersClient.getStatus());
		}
		List<NewsPaper> listNews = newsPapersClient
				.getEntity(new GenericType<List<NewsPaper>>() {
				});
		// Get Advertisements
		WebResource adsResource = getWebResource("advertisements");
		ClientResponse adsClient = adsResource.type("application/json").get(
				ClientResponse.class);
		if (adsClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ adsClient.getStatus());
		}
		List<Advertisement> listAds = adsClient
				.getEntity(new GenericType<List<Advertisement>>() {
				});
		// Iterate through the list of newsads and filter the newspaper and ads.
		Map<Map<String, String>, Map<String, String>> reportMap = new HashMap<Map<String, String>, Map<String, String>>();
		for (NewsAds newsAd : listNewsAds) {
			Map<String, String> newsMap = new HashMap<String, String>();
			Map<String, String> adsMap = new HashMap<String, String>();
			for (NewsPaper newsPaper : listNews) {
				if (newsPaper.getNewsPaperId() == newsAd.getNewsPaperId()) {
					newsMap.put(Integer.toString(newsPaper.getNewsPaperId()),
							newsPaper.getNewsPaperName());
					break;
				}
			}
			for (Advertisement adv : listAds) {
				if (adv.getAdvId() == newsAd.getAdvId()) {
					adsMap.put(Integer.toString(adv.getAdvId()),
							adv.getAdvName());
					break;
				}
			}
			if (newsMap.size() == 1 && adsMap.size() == 1) {
				reportMap.put(newsMap, adsMap);
			}
		}
		model.addAttribute("reportData", reportMap);

		return "ViewNewsAds";
	}

	/**
	 * This resource finds the newspaper is already exist or not. *
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findnewspaper", method = RequestMethod.POST)
	public void isNewsPaperExist(ModelMap model,
			@RequestParam("paperName") String paperName,
			@RequestParam("newsContactNbr") String contactNbr) {
		NewsPaper newsPaper = new NewsPaper();
		newsPaper.setNewsPaperName(paperName);
		newsPaper.setContactNbr(contactNbr);
		// Get NewsPapers
		WebResource newsPapaersResource = getWebResource("newspapers");
		ClientResponse newsPapersClient = newsPapaersResource.type(
				"application/json").get(ClientResponse.class);
		if (newsPapersClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ newsPapersClient.getStatus());
		}
		List<NewsPaper> listNews = newsPapersClient
				.getEntity(new GenericType<List<NewsPaper>>() {
				});
		if (listNews.contains(newsPaper)) {
			model.addAttribute("isExist", true);
		} else {
			model.addAttribute("isExist", false);
		}
	}

	/**
	 * This resource finds the advertisement is already exist or not. *
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findadvertisement", method = RequestMethod.POST)
	public void isAdvertisementExist(ModelMap model,
			@RequestParam("advertisementName") String advertisementName,
			@RequestParam("adContactNbr") String advContactNbr,
			@RequestParam("adDetails") String advDetails) {
		Advertisement advertisement = new Advertisement();
		advertisement.setAdvContactNbr(advContactNbr);
		advertisement.setAdvDetails(advDetails);
		advertisement.setAdvName(advertisementName);
		// Get Advertisements
		WebResource adsResource = getWebResource("advertisements");
		ClientResponse adsClient = adsResource.type("application/json").get(
				ClientResponse.class);
		if (adsClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ adsClient.getStatus());
		}
		List<Advertisement> listAds = adsClient
				.getEntity(new GenericType<List<Advertisement>>() {
				});
		if (listAds.contains(advertisement)) {
			model.addAttribute("isExist", true);
		} else {
			model.addAttribute("isExist", false);
		}
	}

	@RequestMapping(value = "/findnewsadvertisement", method = RequestMethod.POST)
	public void isAdvertisementExistInNewsPaper(ModelMap model,
			@RequestParam("advertisementName") String advertisementName,
			@RequestParam("paperName") String paperName) {
		// Get NewsAdvertisements
		WebResource newsPapaerAdsResource = getWebResource("newspapersads");
		ClientResponse newsPaperAdsClient = newsPapaerAdsResource.type(
				"application/json").get(ClientResponse.class);
		if (newsPaperAdsClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ newsPaperAdsClient.getStatus());
		}
		List<NewsAds> listNewsAds = newsPaperAdsClient
				.getEntity(new GenericType<List<NewsAds>>() {
				});

		NewsPaper newsPaper = getNewsPaper(paperName);
		Advertisement advt = getAdvertisement(advertisementName);
		model.addAttribute("isExist", false);
		for (NewsAds newsAds : listNewsAds) {
			if (newsAds.getNewsPaperId() == newsPaper.getNewsPaperId()
					&& newsAds.getAdvId() == advt.getAdvId()) {
				model.addAttribute("isExist", true);
				break;
			}
		}
	}

	@RequestMapping(value = "/createnewsadswithnames", method = RequestMethod.POST)
	public String createAdvertisementNewsPaperNames(ModelMap model,
			@RequestParam("advertisementName") String advertisementName,
			@RequestParam("paperName") String paperName) {
		NewsPaper newsPaper = getNewsPaper(paperName);
		Advertisement advt = getAdvertisement(advertisementName);
		model.addAttribute("newspaper", newsPaper.getNewsPaperId());
		model.addAttribute("advertisement", advt.getAdvId());
		return createNewsPaperAdvertisement(model,
				Integer.toString(newsPaper.getNewsPaperId()),
				Integer.toString(advt.getAdvId()));
	}

	private NewsPaper getNewsPaper(String paperName) {
		// Get NewsPapers
		NewsPaper paper = null;
		WebResource newsPapaersResource = getWebResource("newspapers");
		ClientResponse newsPapersClient = newsPapaersResource.type(
				"application/json").get(ClientResponse.class);
		if (newsPapersClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ newsPapersClient.getStatus());
		}
		List<NewsPaper> listNews = newsPapersClient
				.getEntity(new GenericType<List<NewsPaper>>() {
				});
		for (NewsPaper newsPaper : listNews) {
			if (newsPaper.getNewsPaperName().equalsIgnoreCase(paperName)) {
				paper = newsPaper;
				break;
			}
		}
		return paper;
	}

	private Advertisement getAdvertisement(String advName) {
		Advertisement advt = null;
		// Get Advertisements
		WebResource adsResource = getWebResource("advertisements");
		ClientResponse adsClient = adsResource.type("application/json").get(
				ClientResponse.class);
		if (adsClient.getStatus() != 200) {
			throw new RuntimeException(
					"Unable to get newspapers : HTTP error code : "
							+ adsClient.getStatus());
		}
		List<Advertisement> listAds = adsClient
				.getEntity(new GenericType<List<Advertisement>>() {
				});

		for (Advertisement adv : listAds) {
			if (adv.getAdvName().equalsIgnoreCase(advName)) {
				advt = adv;
				break;
			}
		}
		return advt;
	}

	public WebResource getWebResource(String resourceUri) {
		Client client = Client.create();
		WebResource webResource = client
				.resource("http://localhost:7001/acme-ads-service/api/"
						+ resourceUri);
		return webResource;
	}
}
