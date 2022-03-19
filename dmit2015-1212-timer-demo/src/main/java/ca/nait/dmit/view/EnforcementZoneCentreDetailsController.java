package ca.nait.dmit.view;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import ca.nait.dmit.repository.EnforcementZoneCentreRepository;

import lombok.Getter;
import lombok.Setter;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.omnifaces.util.Faces;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Named("currentEnforcementZoneCentreDetailsController")
@ViewScoped
public class EnforcementZoneCentreDetailsController implements Serializable {
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
}