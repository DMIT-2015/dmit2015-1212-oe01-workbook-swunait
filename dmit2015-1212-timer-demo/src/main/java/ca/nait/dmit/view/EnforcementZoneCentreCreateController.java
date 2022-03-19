package ca.nait.dmit.view;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import ca.nait.dmit.repository.EnforcementZoneCentreRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("currentEnforcementZoneCentreCreateController")
@RequestScoped
public class EnforcementZoneCentreCreateController {

    @Inject
    private EnforcementZoneCentreRepository _enforcementzonecentreRepository;

    @Getter
    private EnforcementZoneCentre newEnforcementZoneCentre = new EnforcementZoneCentre();

    public String onCreateNew() {
        String nextPage = "";
        try {
            _enforcementzonecentreRepository.create(newEnforcementZoneCentre);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful. {0}", e.getMessage());
        }
        return nextPage;
    }

}