/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import java.io.File;
import java.io.FileNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.primefaces.json.JSONException;
import org.sbml.jsbml.SBMLDocument;

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "loader")
@SessionScoped
public class Loader implements Serializable {

    /**
     * Creates a new instance of Loader
     */
    @Inject
    Compounds compounds;
    @Inject
    Reactions reactions;
    @Inject
    ModelGraph modelg;
    @Inject
    TabView tabView;
    String keyword;
    String organism;
    SABIO sabio;
    List<String> organismList;
    List<Organism> orgnaisms = new ArrayList<Organism>();
    SBMLDocument sbmldoc;
    String result;
    String compoundstring;
    String reactionstring;
    public Loader() throws FileNotFoundException {
        //  organismList = new ArrayList<String>();
        //List<Organism> orgnaism = new ArrayList<Organism>();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String filename = servletContext.getRealPath("") + File.separator + "organisms.txt";
        Scanner scan = new Scanner(new File(filename));//"C:\\Users\\Mathialakan.Thavappi\\Documents\\ResearchWork\\modelcreation\\modeller\\organisms.txt"));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            Organism org = new Organism();
            String[] words = line.split("\t");
            org.setId(words[0]);
            org.setKey(words[1]);
            String name = words[2];//.substring(0, words[2].indexOf("("));
            String oname = "";
            if (name.contains("(")) {
                name = name.substring(0, words[2].indexOf("("));
                oname = words[2].substring(words[2].indexOf("(") + 1, words[2].indexOf(")"));
            }
            org.setName(name);
            org.setOthername(oname);
            org.setKind(words[3]);
            orgnaisms.add(org);
            System.out.println("ww " + words[2]);
            //organismList.add(words[2]);
            //organismList.add("hsa");
        }
    }

    public String getCompoundstring() {
        return compoundstring;
    }

    public void setCompoundstring(String compoundstring) {
        this.compoundstring = compoundstring;
    }

    public String getReactionstring() {
        return reactionstring;
    }

    public void setReactionstring(String reactionstring) {
        this.reactionstring = reactionstring;
    }

    public SBMLDocument getSbmldoc() {
        return sbmldoc;
    }

    public void setSbmldoc(SBMLDocument sbmldoc) {
        this.sbmldoc = sbmldoc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public List<String> getOrganismList() {

        return organismList;
    }

    public void setOrganismList(List<String> organismList) {
        this.organismList = organismList;
    }

    public void load() throws UnsupportedEncodingException, JSONException {//throws JSONException{
        //if(this.keyword.trim()!= "")
        //if()this.organism.trim()=="")
        KEGG kegg = new KEGG();
        //get a list of compounds
        String keggOrg = "";
        String sabioOrg = "";
        for (Organism org : orgnaisms) {
            if (org.getName().equals(organism.trim()) || org.getOthername().equals(organism.trim())) {
                keggOrg = org.getKey();
                sabioOrg = org.getName();
            }
        }
        sabio = new SABIO();
        ArrayList<Compound> keggcompoundList = kegg.getCompoundList(keyword.trim(), keggOrg);// sabio.getCompundList(kegg.getCompoundList(keyword.trim(), keggOrg));
        this.compoundstring = convertToString(keggcompoundList);
        //keggcompoundList = sabio.getCompundList(keggcompoundList);
        System.out.println("k " + keggcompoundList.size());
        compounds.setCompoundList(keggcompoundList);

        //get reactions as an SBML model 
        if (sabio.isWorks()) {
            SBMLDocument doc = sabio.getKinetcLaw(keyword.trim(), sabioOrg);
            sbmldoc = doc.clone();
            System.out.println("doc :" + doc);
//        System.out.println("rr "+sabio.getReactionList().size());
//        //get reactions for selected compounds
            if (doc != null) {
                ArrayList<String> reactionList = sabio.getReactionList(keggcompoundList);
                reactions.setReactionList(reactionList);
                this.reactionstring = listToString(reactionList);
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String filename = servletContext.getRealPath("") + File.separator + "mybiomodel.xml";
                SBMLDocument cdoc = sabio.createModel(reactionList);
                this.result = new FileCtrl().writeString(cdoc);
                new FileCtrl().write(cdoc, filename);
                SBMLModel sbmlmodel = new SBMLModel(cdoc);
                modelg.setModeljson(sbmlmodel.createJson());
            }
        }
        tabView.setIndex(0);
    }

    public void loadCompounds() {
        KEGG kegg = new KEGG();
        //get a list of compounds

        ArrayList<Compound> keggcompoundList = kegg.getCompoundList(keyword, organism);
        compounds.setCompoundList(keggcompoundList);
    }

    public void createMOdel() {
        sabio.createModel((ArrayList) reactions.getSelectedList());
    }

    public void itemChangeListener(ValueChangeEvent event) {
        this.keyword = event.getNewValue().toString();
    }

    public List<String> generateOrgList(String query) {
        List<String> results = new ArrayList<String>();
        for (Organism org : orgnaisms) {
            if (org.getName().startsWith(query)) {
                results.add(org.getName());
            } else if (org.getOthername().startsWith(query)) {
                results.add(org.getOthername());
            }
        }

        return results;
    }

    public void modify() throws JSONException {
//        if (modelg.isLocal()) {
//            
//        } else {
        System.out.println("********");
        SBMLDocument doc = sbmldoc.clone();
        sabio.setSbmldocument(doc);

        ArrayList<String> reactionList = sabio.getReactionList((ArrayList) compounds.getSelectedList());
        //reactions.setReactionList(reactionList);
        //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String filename = servletContext.getRealPath("") + File.separator + "mycurrentbiomodel.xml";
        // new FileCtrl().write(sabio.createModel((ArrayList) reactions.getSelectedList()), filename);
        SBMLDocument cdoc = sabio.createModel((ArrayList) reactions.getSelectedList());
        this.result = new FileCtrl().writeString(cdoc);
        SBMLModel sbmlmodel = new SBMLModel(cdoc);
        modelg.setModeljson(sbmlmodel.createJson());
        // }
    }

    private String convertToString(ArrayList<Compound> list) {
        StringBuilder sb = new StringBuilder();
        for (Compound met : list) {
            //System.out.println(met.getId());
            sb.append(met.getId() + "\t");
            if (met.getNumberOfNames() > 0) {
                for (String syn : met.getNames()) {
                    sb.append(syn + "\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private String listToString(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String ele : list) {
            sb.append(ele + "\n");
        }
        return sb.toString();
    }
}
