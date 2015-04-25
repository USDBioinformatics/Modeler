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
@Named(value = "reactions")
@SessionScoped
public class Reactions implements Serializable {

    /**
     * Creates a new instance of Reactions
     */
    List<String> reactionList;
    List<String> selectedList;
    public Reactions() {
        this.reactionList = new ArrayList<String>();
        this.selectedList = new ArrayList<String>();
    }

    public List<String> getReactionList() {
        return reactionList;
    }

    public void setReactionList(List<String> reactionList) {
        this.reactionList = reactionList;
    }

    public List<String> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<String> selectedList) {
        this.selectedList = selectedList;
    }
    
}
