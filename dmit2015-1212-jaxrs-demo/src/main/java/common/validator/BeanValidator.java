package common.validator;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.Validation;

/**
 * This class contains a single class-level (static) method checking for Bean Validation constraint annotation violations.
 * <p>
 * The following example shows to validate a Region object named newRegion.
 * <p>
 * String errorMessage = BeanValidator.validateBean(Region.class, newRegion);
 * if (errorMessage != null) {
 * return Response
 * .status(Response.Status.BAD_REQUEST)
 * .entity(errorMessage)
 * .build();
 * }
 *
 * @version 2022.02.06
 */
public class BeanValidator {

    public static <T> String validateBean(Class<T> classType, T typeInstance) {
        String errorMessage = null;
        var constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(typeInstance);
        if (constraintViolations.size() > 0) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            for (var singleConstraintViolation : constraintViolations) {
                jsonObjectBuilder.add(singleConstraintViolation.getPropertyPath().toString(), singleConstraintViolation.getMessage());
            }
            errorMessage = jsonObjectBuilder.build().toString();
        }
        return errorMessage;
    }
}

