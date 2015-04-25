/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "speciesSelection")
@SessionScoped
public class SpeciesSelection implements Serializable{

    /**
     * Creates a new instance of SpeciesSelection
     */
    List<String> namelist = new ArrayList<String>();
    List<String> selectedlist = new ArrayList<String>();
    public SpeciesSelection() {
    }

    public List<String> getNamelist() {
        return namelist;
    }

    public void setNamelist(List<String> namelist) {
        this.namelist = namelist;
    }

    public List<String> getSelectedlist() {
        return selectedlist;
    }

    public void setSelectedlist(List<String> selectedlist) {
        this.selectedlist = selectedlist;
    }
    
}
