package org.spo.fw.utils.pg.model;

import org.spo.fw.exception.SPOException;
import org.spo.fw.log.Logger1;
import org.spo.fw.navigation.itf.Page;
import org.spo.fw.utils.pg.itf.WebContentProvider;
import org.spo.fw.utils.pg.util.ContentUtils;
import org.spo.fw.web.KeyWords;

public class DefaultWebContentProvider implements WebContentProvider {
	Logger1 log = new Logger1(this.getClass().getSimpleName());
	@Override
	public String getPageContent(String pageName, KeyWords kw) {

		String content;
	
		try {			
			Page page = kw.impl_nav.getNavContainer().getModel().getFactory().getPage(pageName);
			String identifier = page.getIdentifier();
			String formKeyVals = page.getFormData(kw);
			String fromPage = kw.doPrintPageAsText();			
			String pageText = util_getPageText(fromPage,  identifier);
			content = pageText+formKeyVals;

		} catch (SPOException e) {			
			log.debug("Page object was not found hence proceding ");
			e.printStackTrace();
			content = kw.doPrintPageAsText();
		}


		return content;


	}

	public String util_getPageText(String pageText, String identifier){
		String identifier_sansSpace = identifier.trim();
		if(!identifier_sansSpace.isEmpty() && pageText.contains(identifier_sansSpace)){
			String[] splits= pageText.split(identifier_sansSpace);
			splits[0]="";
			StringBuffer buf = new StringBuffer();
			for(String x :splits){
				buf.append(x);
			}
			return identifier_sansSpace+buf.toString();
		}else{
			return pageText;
		}

	}
}
