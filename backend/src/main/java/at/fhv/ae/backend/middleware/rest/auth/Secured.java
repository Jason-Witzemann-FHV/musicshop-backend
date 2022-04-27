package at.fhv.ae.backend.middleware.rest.auth;

import at.fhv.ae.backend.domain.model.user.Permission;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {

    Permission value() default Permission.NONE; // annotations do not accept null as default, therefore we are getting creative! :)

}