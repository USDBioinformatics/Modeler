/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "tabView")
@SessionScoped
public class TabView implements Serializable {

    /**
     * Creates a new instance of TabView
     */
    int index=-1;
    public TabView() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
