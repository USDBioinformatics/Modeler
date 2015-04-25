/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "fileDownload")
@SessionScoped
public class FileDownload implements Serializable {

    /**
     * Creates a new instance of FileDownload
     */
    private StreamedContent file;
    private StreamedContent compounds;
    private StreamedContent reactions;
    int numb = 0;
    @Inject
    Loader loader;
    public FileDownload() {
        //download();
    }

    public void download() {
        numb++;
        String bm = "newmodel" + String.valueOf(numb);
        String content = loader.getResult();
        System.out.println("@: "+ content);
        InputStream stream = new ByteArrayInputStream(content.getBytes());
        file = new DefaultStreamedContent(stream, "text/plain", bm + ".xml"); 
        
    }

    public void downloadCompounds() {
        numb++;
        String bm = "compounds" + String.valueOf(numb);
        String content = loader.getCompoundstring();
        InputStream stream = new ByteArrayInputStream(content.getBytes());
        compounds = new DefaultStreamedContent(stream, "text/plain", bm + ".xml"); 
        
    }
    public void downloadReactions() {
        numb++;
        String bm = "reactions" + String.valueOf(numb);
        String content = loader.getReactionstring();
        InputStream stream = new ByteArrayInputStream(content.getBytes());
        reactions = new DefaultStreamedContent(stream, "text/plain", bm + ".xml"); 
        
    }
    public StreamedContent getCompounds() {
        return compounds;
    }

    public void setCompounds(StreamedContent compounds) {
        this.compounds = compounds;
    }

    public StreamedContent getReactions() {
        return reactions;
    }

    public void setReactions(StreamedContent reactions) {
        this.reactions = reactions;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}
