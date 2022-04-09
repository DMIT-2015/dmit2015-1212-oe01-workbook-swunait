package dmit2015.security;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

public class MovieSecurityInterceptor {

    @Inject
    private Security _security;

    @AroundInvoke
    private Object logMethod(InvocationContext ic) throws Exception {
        String methodName = ic.getMethod().getName();
        if (methodName.startsWith("create")) {
            boolean isInApprovedRole = _security.isInAnyRole("Sales");
            if (!isInApprovedRole) {
                throw new RuntimeException("Access denied. You do not have permission to create movie");
            }
        }

        if (methodName.startsWith("delete")) {
            boolean isInApprovedRole = _security.isInAnyRole("IT");
            if (!isInApprovedRole) {
                throw new RuntimeException("Access denied. You do not have permission to delete a movie");
            }
        }

        if (methodName.startsWith("list")) {
            boolean isInApprovedRole = _security.isInAnyRole("Sales","Shipping","IT");
            if (!isInApprovedRole) {
                throw new RuntimeException("Access denied. You do not have permission to list movies");
            }
        }

        return ic.proceed();
    }
}
