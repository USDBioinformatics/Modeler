/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


import javax.servlet.ServletContext;
import org.primefaces.json.JSONException;
import org.sbml.jsbml.SBMLDocument;
//import org.apache.commons.fileupload.FileUpload

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "fileUpload")
@SessionScoped
public class FileUpload implements Serializable {

    /**
     * Creates a new instance of FileUpload
     */
    @Inject
    ModelGraph modelgraph;
    @Inject
    Loader loader;
    @Inject
    Reactions reactions;
    @Inject
    Compounds compounds;
    String fileName;
    int filesOnline = 0;
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) throws JSONException {
        System.out.println("uploading...");
        UploadedFile file = event.getFile();
        if (file != null) {
            //FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            // FacesContext.getCurrentInstance().addMessage(null, msg);
            filesOnline++;
            if (uploadFile(file)) {
                //modelgraph.setLocal(true);
                SBMLDocument doc= new FileCtrl().read(fileName);
                loader.setSbmldoc(doc);
                SBMLModel sbmlmodel = new SBMLModel(doc);
                modelgraph.setModeljson(sbmlmodel.createJson());
                compounds.setCompoundList(sbmlmodel.compoundList());
                reactions.setReactionList(sbmlmodel.reactionNameList());
            }
        } else {
            System.out.println("updated null  ");
        }
    }

//    public void upload() throws JSONException {
//        System.out.println("uploading...");
//        if (file != null) {
//            //FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//            // FacesContext.getCurrentInstance().addMessage(null, msg);
//            filesOnline++;
//            if (uploadFile(file)) {
//                modelgraph.setModeljson(new SBMLModel(fileName).createJson());
//            }
//        } else {
//            System.out.println("updated null  ");
//        }
//    }

    private boolean uploadFile(UploadedFile file) {

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        setFileName(servletContext.getRealPath("") + File.separator + file.getFileName());
        System.out.println("path...comp :" + fileName);
        File temp = new File(fileName);
        try {
            OutputStream outs = new FileOutputStream(temp);
            outs.write(file.getContents(), 0, file.getContents().length);
            outs.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
