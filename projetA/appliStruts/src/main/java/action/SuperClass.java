package action;

import packageFacade.Facade;
import packageFacade.IFacade;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * @author Anthony
 */
public abstract class SuperClass extends ActionSupport implements ApplicationAware, SessionAware {

    protected IFacade facade;
    protected Map<String, Object> sessionMap;

    public void setApplication(Map<String, Object> map) {
        this.facade = (IFacade) map.get("facade");
        if (this.facade == null) {
            this.facade = Facade.getInstance();
            map.put("facade", this.facade);
        }
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }

}
