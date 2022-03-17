package ca.nait.dmit.view;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import ca.nait.dmit.repository.EnforcementZoneCentreRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("currentEnforcementZoneCentreListController")
@ViewScoped
public class EnforcementZoneCentreListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EnforcementZoneCentreRepository _enforcementzonecentreRepository;

    @Getter
    private List<EnforcementZoneCentre> enforcementzonecentreList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            enforcementzonecentreList = _enforcementzonecentreRepository.list();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}