package ca.nait.dmit.batch;

import ca.nait.dmit.entity.EnforcementZoneCentre;
import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.inject.Named;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

@Named
public class EdmontonZoneCentreItemProcessor implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        String line = (String) item;
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

        return currentEnforcementZoneCentre;
    }
}
