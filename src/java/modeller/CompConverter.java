/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modeller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;

/**
 *
 * @author Mathialakan.Thavappi
 */
@Named(value = "compConverter")
@SessionScoped
public class CompConverter implements Serializable, Converter  {

    /**
     * Creates a new instance of CompConverter
     */
   @Inject
    Compounds compoundlist;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        System.out.println("ss : " + submittedValue);
        if (submittedValue != null && submittedValue.trim().length() > 0) {
            try {
                //int number = Integer.parseInt(submittedValue);
                for (Compound c : compoundlist.getCompoundList()) {
                    System.out.println("id: "+ c.getId());
                    if (c.getId().equalsIgnoreCase(submittedValue)) {
                        System.out.println("id: "+ c.getId());
                        return c;
                    }
                }
                return null;
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        } else {

            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Compound) value).getId());
        }
    }
}
