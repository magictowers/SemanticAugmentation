package at.jku.cis.wisch.paa.api.query.wikipedia;

import java.util.Set;

import at.jku.cis.wisch.paa.storage.PaaStorage;

public class WikipediaVisits {

	public static String getVisitedLanguages(PaaStorage storage, String user) {
		// TODO get all wikipedia.org visits from database
		String urlPattern = ".wikipedia.org";
		
		Set<String> urls = storage.getURLsMatchingPattern(user, urlPattern);

		// TODO get the unique languages that were at least visited 'threshold'??? times
		
		String langsString = "";
		//println(url.indexOf("%2F%2F"));
		//println(url.indexOf(urlPattern));
		if (urls.size() >= 1) {
			for (String url : urls) {
				langsString += url.substring(url.indexOf("%2F%2F")+6, url.indexOf(urlPattern))+ ", ";
			}
			langsString = langsString.substring(0, langsString.length()-2);
		}
		
		//println(langsString);
		
		return langsString;
		
	}
	

}
