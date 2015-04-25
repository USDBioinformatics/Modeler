/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "compounds")
@SessionScoped
public class Compounds implements Serializable {

    /**
     * Creates a new instance of Compounds
     */
     List<Compound> compoundList;
     List<Compound> selectedList;
    public Compounds() {
       compoundList = new ArrayList<Compound>();
       selectedList = new ArrayList<Compound>();
    }

    public List<Compound> getCompoundList() {
        return compoundList;
    }

    public void setCompoundList(List<Compound> compoundList) {
        this.compoundList = compoundList;
    }

    public List<Compound> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<Compound> selectedList) {
        this.selectedList = selectedList;
    }
    
}
