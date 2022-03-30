package dmit2015.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleController implements Serializable {

    @Getter
    private Locale currentLocale;

    @Getter
    private Locale defaultLocale;

    @Getter
    private String languageTag;

    @Getter
    private List<Locale> availableLocales = new ArrayList<>();

    @PostConstruct
    public void init() {
        defaultLocale = Faces.getDefaultLocale();
        currentLocale = Faces.getApplication().getViewHandler().calculateLocale(Faces.getContext());
        // Add all the locales defined in faces-config.xml
//        availableLocales.add(Faces.getDefaultLocale());
        Faces.getSupportedLocales().forEach(availableLocales::add);
    }

    public void reload() {
        // The JavaScript "locaation.replace(locaton)" is used to reload the current document without keeping the previous document in history.
        Faces.getContext().getPartialViewContext().getEvalScripts().add("location.replace(location)");
    }

    public void setLanguageTag(String languageTag) {
        currentLocale = Locale.forLanguageTag(languageTag);
        this.languageTag = languageTag;
    }
}
