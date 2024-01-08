package org.codingweek;

import org.codingweek.model.Page;

/** singleton class to store application context
 * in link with javaFX implementation */
public class ApplicationContext {

    private static ApplicationContext instance;

    private Page pageType;

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public void setPageType(Page pageType){
        this.pageType = pageType;
    }

    public Page getPageType(){
        return this.pageType;
    }

}
