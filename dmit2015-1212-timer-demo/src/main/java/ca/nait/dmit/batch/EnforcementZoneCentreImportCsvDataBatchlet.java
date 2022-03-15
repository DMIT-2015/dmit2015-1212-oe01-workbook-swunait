package ca.nait.dmit.batch;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import ca.nait.dmit.repository.EnforcementZoneCentreRepository;
import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.runtime.BatchStatus;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;

@Named
public class EnforcementZoneCentreImportCsvDataBatchlet extends AbstractBatchlet {

    @Inject
    private EnforcementZoneCentreRepository _enforcementRepository;

    @Inject
    @ConfigProperty(name = "enforcement.csv.filepath")
    private String csvFilePath;

    @Override
    public String process() throws Exception {

        _enforcementRepository.deleteAll();
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(csvFilePath).toFile()))) {
            // Skip the first line by reading it since it contains column names only
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {

                try {
                    final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
                    String[] tokens = line.split(delimiter, -1);

                    EnforcementZoneCentre currentEnforcementZoneCentre = new EnforcementZoneCentre();
                    currentEnforcementZoneCentre.setSiteId(Short.parseShort(tokens[0]));
                    currentEnforcementZoneCentre.setLocationDescription(tokens[1]);
                    currentEnforcementZoneCentre.setSpeedLimit(Short.parseShort(tokens[2]));
                    currentEnforcementZoneCentre.setReasonCodes(tokens[3].replaceAll("[\"()]", ""));
                    currentEnforcementZoneCentre.setLatitude(Double.valueOf(tokens[4]));
                    currentEnforcementZoneCentre.setLongitude(Double.valueOf(tokens[5]));

//        String wktText = "POINT" + tokens[6].replaceAll("[\",]","");
//        Point geoLocation = (org.locationtech.jts.geom.Point) new WKTReader().read(wktText);
//        currentEnforcementZoneCentre.setGeoLocation(geoLocation);

                    Point geoLocation = new GeometryFactory().createPoint(
                            new Coordinate(
                                    currentEnforcementZoneCentre.getLongitude(), currentEnforcementZoneCentre.getLatitude()
                            )
                    );
                    currentEnforcementZoneCentre.setGeoLocation(geoLocation);

                    _enforcementRepository.create(currentEnforcementZoneCentre);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
        return BatchStatus.COMPLETED.toString();
    }
}
