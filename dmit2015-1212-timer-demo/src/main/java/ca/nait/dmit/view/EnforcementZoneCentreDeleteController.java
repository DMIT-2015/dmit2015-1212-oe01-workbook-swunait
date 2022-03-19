package ca.nait.dmit.view;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import ca.nait.dmit.repository.EnforcementZoneCentreRepository;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Optional;

@Named("currentEnforcementZoneCentreDeleteController")
@ViewScoped
public class EnforcementZoneCentreDeleteController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EnforcementZoneCentreRepository _enforcementzonecentreRepository;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Short editId;

    @Getter
    private EnforcementZoneCentre existingEnforcementZoneCentre;

    @PostConstruct
    public void init() {
        Optional<EnforcementZoneCentre> optionalEnforcementZoneCentre = _enforcementzonecentreRepository.findOptional(editId);
        optionalEnforcementZoneCentre.ifPresentOrElse(
                existingItem -> existingEnforcementZoneCentre = existingItem,
                () -> Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index.xhtml")
        );
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _enforcementzonecentreRepository.delete(existingEnforcementZoneCentre.getSiteId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}