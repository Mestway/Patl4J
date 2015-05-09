package goofs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GoofsProperties {

	protected Properties props;

	protected List<String> languages = new ArrayList<String>();

	public static final GoofsProperties INSTANCE = new GoofsProperties();

	protected GoofsProperties() {

		setProps(new Properties());

		try {
			getProps().load(
					getClass().getClassLoader().getResourceAsStream(
							"goofs-default.properties"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			getProps().load(
					new FileInputStream(new java.io.File(System
							.getProperty("user.home"), ".goofs.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BufferedReader languageReader = new BufferedReader(
					new InputStreamReader(getClass().getClassLoader()
							.getResourceAsStream("translate.properties")));
			String lang = languageReader.readLine();
			while (lang != null) {
				languages.add(lang.trim());
				lang = languageReader.readLine();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	protected Properties getProps() {
		return props;
	}

	protected void setProps(Properties props) {
		this.props = props;
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}

	public List<String> getLanguages() {
		return languages;
	}

}
