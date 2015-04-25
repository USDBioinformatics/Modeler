/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "modelGraph")
@SessionScoped
public class ModelGraph implements Serializable {

    /**
     * Creates a new instance of ModelGraph
     */
    String modeljson;
    boolean local=false;
    public ModelGraph() throws JSONException {
        this.modeljson = "";
//        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//        System.out.println("path...comp :" + servletContext.getRealPath(""));
//         SBMLModel sbmlmodel = new SBMLModel(servletContext.getRealPath("") + File.separator + "mybiomodel.xml");
//        this.modeljson = sbmlmodel.createJson();
    }

    @PostConstruct
    public void init() throws JSONException {
        // new SBMLModel().createJson("mymodel.xml");
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getModeljson()  {
        
        return modeljson;
    }

    public void setModeljson(String modeljson) {
        this.modeljson = modeljson;
    }

    public void onTabChange(TabChangeEvent event) throws JSONException {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("onTabChange");

    }

    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("onTabClose");
    } 
}
