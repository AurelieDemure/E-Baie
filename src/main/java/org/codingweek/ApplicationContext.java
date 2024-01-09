package org.codingweek;

import org.codingweek.model.Page;
import org.codingweek.model.User;

/** singleton class to store application context
 * in link with javaFX implementation */
public class ApplicationContext {

    private static ApplicationContext instance;

    /**
     * user currently authentified
     * null if no user is authentified
     */
    private User user_authentified;

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

    public User getUser_authentified() {
        return user_authentified;
    }

    public void setUser_authentified(User user_authentified) {
        this.user_authentified = user_authentified;
    }
}
