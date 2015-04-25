/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;

/**
 *
 * @author Mathialakan.Thavappi
 */
public class SBMLModel {

    ListOf<Species> speciesList;
    ListOf<Reaction> reactionList;
    SBMLDocument sbmldocument;
    public SBMLModel(SBMLDocument sbmldocument) {
        //System.out.println("filename " + filename);
       //this.sbmldocument = sbmldocument;//new FileCtrl().read(filename);
        if (sbmldocument != null) {
            if (sbmldocument.getModel() != null) {
                this.speciesList = sbmldocument.getModel().getListOfSpecies();
                this.reactionList = sbmldocument.getModel().getListOfReactions();
            } else {
                this.speciesList = new ListOf<Species>();
                this.reactionList = new ListOf<Reaction>();
            }
        }
    }

    public SBMLDocument getSbmldocument() {
        return sbmldocument;
    }

    public void setSbmldocument(SBMLDocument sbmldocument) {
        this.sbmldocument = sbmldocument;
    }

    public ListOf<Species> getSpeciesList() {
        return speciesList;
    }

    public void setSpeciesList(ListOf<Species> speciesList) {
        this.speciesList = speciesList;
    }

    public ListOf<Reaction> getReactionList() {
        return reactionList;
    }

    public void setReactionList(ListOf<Reaction> reactionList) {
        this.reactionList = reactionList;
    }

    public String createJson() throws JSONException {
//        System.out.println("filename " + filename);
        String json = "";
//        SBMLDocument sbmldocument = new FileCtrl().read(filename);//new SABIO().getKinetcLaw("folate","Homo sapiens");

        // if (sbmldocument != null) {
        String colour = randomColor();
        JSONObject nodedata;
        JSONObject edgedata;
        JSONArray nodelist = new JSONArray();
        JSONArray edgelist = new JSONArray();
        //if (sbmldocument.getModel() != null) {
        for (Species species : this.speciesList) {
            String name = species.getName();
            if ("".equals(name)) {
                name = species.getId();
            }
            nodedata = new JSONObject();
            nodedata.put("data", creatNode(species.getId(), name, new Integer(100), new Integer(100), colour, "ellipse"));
            nodelist.put(nodedata);
        }
        for (Reaction reaction : this.reactionList) {
            String rid = reaction.getId();
            String name = reaction.getName();
            if ("".equals(name)) {
                name = reaction.getId();
            }
            nodedata = new JSONObject();
            nodedata.put("data", creatNode(rid, name, new Integer(100), new Integer(100), colour, "triangle"));
            nodelist.put(nodedata);
            boolean reversible = reaction.isReversible();
            for (SpeciesReference sp : reaction.getListOfReactants()) {
                Species species = sp.getSpeciesInstance();
                String spid = species.getId();
                edgedata = new JSONObject();
                if (reversible) {
                    edgedata.put("data", creatEdge(spid, rid, colour, "triangle"));
                } else {
                    edgedata.put("data", creatEdge(spid, rid, colour, ""));
                }
                edgelist.put(edgedata);
            }
            for (SpeciesReference sp : reaction.getListOfProducts()) {
                Species species = sp.getSpeciesInstance();
                String spid = species.getId();
                edgedata = new JSONObject();
                if (reversible) {
                    edgedata.put("data", creatEdge(rid, spid, colour, "triangle"));
                } else {
                    edgedata.put("data", creatEdge(rid, spid, colour, ""));
                }
                edgelist.put(edgedata);
            }
        }
        JSONObject nodes = new JSONObject();
        nodes.put("nodes", nodelist);
        JSONObject edges = new JSONObject();
        edges.put("edges", edgelist);
        json = "{ nodes: " + nodelist.toString() + ", edges:" + edgelist.toString() + " }";
        //}
        //}
        System.out.println("json: " + json);
        return json;
    }

    public JSONObject creatNode(String id, String name, Integer weight, Integer height, String color, String shape) throws JSONException {
        JSONObject node = new JSONObject();
        node.put("id", id);
        node.put("name", name);
        node.put("weight", weight);
        node.put("height", height);
        node.put("faveColor", color);
        node.put("faveShape", shape);
        return node;
    }

    public JSONObject creatEdge(String source, String target, String color, String shape) throws JSONException {
        JSONObject edge = new JSONObject();
        edge.put("source", source);
        edge.put("target", target);
        edge.put("faveColor", color);
        edge.put("faveShape", shape);
        return edge;
    }

    private String randomColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        return "rgb(" + r + "," + g + "," + b + ")";
    }

    public List<String> speciesNameList() {
        List<String> speciesnamelist = new ArrayList<String>();
        for (Species species : this.speciesList) {
            String name = species.getName();
            if ("".equals(name)) {
                name = species.getId();
            }
            speciesnamelist.add(name);
        }
        System.out.println("species: " + speciesnamelist);
        return speciesnamelist;
    }

    public List<Compound> compoundList() {
        List<Compound> compoundlist = new ArrayList<Compound>();
        for (Species species : this.speciesList) {
            Compound comp = new Compound();
            comp.setId(species.getId());
            if (species.isSetName()) {
                comp.setName(species.getName());
                ArrayList<String> names = new ArrayList<String>();
                names.add(species.getName());
                comp.setNames(names);
            }
            compoundlist.add(comp);
        }
        //System.out.println("species: " + speciesnamelist);
        return compoundlist;
    }

    public List<String> reactionNameList() {
        List<String> reactionnamelist = new ArrayList<String>();
        for (Reaction reaction : this.reactionList) {
//            String name = reaction.getName();
//            if ("".equals(name)) {
//                name = reaction.getId();
//            }
            reactionnamelist.add(reaction.getId());
        }
        System.out.println("species: " + reactionnamelist);
        return reactionnamelist;
    }
}
