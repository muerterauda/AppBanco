package language;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public class LocaleBean implements Serializable {

    private final static Locale ENGLISH = new Locale("en");
    private final static Locale SPANISH = new Locale("es");
    private Locale currentLocale;
    private String localeCode;
    private static Map<String, Object> countries;

    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("Spanish", SPANISH);
        countries.put("English", ENGLISH);
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public Locale getCurrentLocale() {
        return (currentLocale);
    }

    public String setEnglish() {
        currentLocale = ENGLISH;
        changeLocale();
        return null;
    }

    public String setSpanish() {
        currentLocale = SPANISH;
        changeLocale();
        return null;
    }

    public Map<String, Object> getCountriesInMap() {
        return countries;
    }

    public String setLocale(ValueChangeEvent e) {

        String newLocaleValue = e.getNewValue().toString();
        if (newLocaleValue.equals("en")) {
            setEnglish();
        } else {
            setSpanish();
        }
        return null;
    }

    private void changeLocale() {
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(currentLocale);
    }

}
