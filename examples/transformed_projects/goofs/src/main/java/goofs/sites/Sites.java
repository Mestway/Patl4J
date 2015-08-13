package goofs.sites;

import goofs.GoofsProperties;

import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.sites.ContentQuery;
import com.google.gdata.client.sites.SitesService;
import com.google.gdata.data.XhtmlTextConstruct;
import com.google.gdata.data.sites.BaseContentEntry;
import com.google.gdata.data.sites.ContentFeed;
import com.google.gdata.data.sites.WebPageEntry;
import com.google.gdata.util.AuthenticationException;

public class Sites implements ISites {

	private SitesService realService;

	private List<SiteInfo> siteInfos;

	private static final String SITES_META = GoofsProperties.INSTANCE
			.getProperty("goofs.sites.meta");

	private static final String SITES_META_SSL = GoofsProperties.INSTANCE
			.getProperty("goofs.sites.meta.ssl");

	public Sites(String userName, String password)
			throws AuthenticationException {

		realService = new SitesService(APP_NAME);
		realService.setUserCredentials(userName, password);
	}

	public void acquireSessionTokens(String username, String password)
			throws AuthenticationException {
		getRealService().setUserCredentials(username, password);

	}

	protected SitesService getRealService() {
		return realService;
	}

	protected void setRealService(SitesService realService) {
		this.realService = realService;
	}

	protected ContentFeed getSiteContentFeed(SiteInfo siteInfo)
			throws Exception {
		String url = buildContentFeedUrl(siteInfo);
		return getRealService().getFeed(new URL(url.toString()),
				ContentFeed.class);
	}

	private String buildContentFeedUrl(SiteInfo siteInfo) {
		StringWriter url = new StringWriter();
		url.append(siteInfo.isSecure() ? "https://" : "http://");
		url.append("sites.google.com/feeds/content/" + siteInfo.getDomain()
				+ "/" + siteInfo.getSiteName() + "/");
		return url.toString();
	}

	public List<WebPageEntry> getRootWebPages(SiteInfo siteInfo)
			throws Exception {
		List<WebPageEntry> rootWebPages = new ArrayList<WebPageEntry>();
		ContentFeed contentFeed = getSiteContentFeed(siteInfo);
		List<WebPageEntry> webPages = contentFeed
				.getEntries(WebPageEntry.class);
		for (WebPageEntry webPage : webPages) {
			if (webPage.getParentLink() == null) {
				rootWebPages.add(webPage);
			}
		}
		return rootWebPages;
	}

	public List<WebPageEntry> getChildWebPages(SiteInfo siteInfo,
			WebPageEntry parent) throws Exception {
		List<WebPageEntry> childWebPages = new ArrayList<WebPageEntry>();
		String url = buildContentFeedUrl(siteInfo);
		ContentQuery query = new ContentQuery(new URL(url));
		query.setParent(getEntryId(parent.getSelfLink().getHref()));
		query.setKind("webpage");
		ContentFeed contentFeed = getRealService().getFeed(query,
				ContentFeed.class);
		List<WebPageEntry> webPages = contentFeed
				.getEntries(WebPageEntry.class);
		for (WebPageEntry webPage : webPages) {
			if (webPage.getParentLink().getHref().equals(
					parent.getSelfLink().getHref())) {
				childWebPages.add(webPage);
			}
		}
		return childWebPages;
	}

	public List<SiteInfo> getSiteInfoList() {
		if (siteInfos == null) {
			siteInfos = new ArrayList<SiteInfo>();
			if (SITES_META != null) {
				String[] configs = SITES_META.split(",");
				for (String config : configs) {
					siteInfos.add(createSiteInfo(config, false));
				}
			}
			if (SITES_META_SSL != null) {
				String[] configs = SITES_META_SSL.split(",");
				for (String config : configs) {
					siteInfos.add(createSiteInfo(config, true));
				}
			}
		}
		return siteInfos;
	}

	private SiteInfo createSiteInfo(String config, boolean secure) {
		String[] parts = config.split("/");
		return new SiteInfo(parts[0], parts[1], secure);
	}

	private String getEntryId(String selfLink) {
		return selfLink.substring(selfLink.lastIndexOf("/") + 1);
	}

	public String getContentBlob(BaseContentEntry<?> entry) {
		return ((XhtmlTextConstruct) entry.getTextContent().getContent())
				.getXhtml().getBlob();
	}

	static public class SiteInfo {
		private String domain;
		private String siteName;
		private boolean secure;

		public SiteInfo(String domain, String siteName, boolean secure) {
			this.domain = domain;
			this.siteName = siteName;
			this.secure = secure;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public String getSiteName() {
			return siteName;
		}

		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}

		public boolean isSecure() {
			return secure;
		}

		public void setSecure(boolean secure) {
			this.secure = secure;
		}
	}

}
