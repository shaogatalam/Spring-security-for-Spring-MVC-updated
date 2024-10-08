package base.pkg.aspect;
import base.pkg.auth.AuthenticationRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class AuthAspect {

     @Before("execution(* base.pkg.auth.AuthenticationService.authenticate(..)) && args(request, response)")
     public void beforeAuthenticate(AuthenticationRequest request, HttpServletResponse response) {
         System.out.println("1 :: Before authenticate method is called...");
         System.out.println(request);
         // You can add additional logic before the authenticate method is invoked
     }

    // @After("execution(* base.pkg.auth.AuthenticationService.authenticate(..)) && args(request, response)")
    // public void afterAuthenticate(AuthenticationRequest request, HttpServletResponse response) {
    //     // Add your aspect logic here
    //     System.out.println("2 :: After authenticate method is called...");
    //     System.out.println("3 :: Response: " + response); // Log the response
    //     // You can add additional logic after the authenticate method is invoked
    // }

    // @Around("execution(* base.pkg.auth.AuthenticationService.authenticate(..))")
    // public Object aroundAuthenticate(ProceedingJoinPoint joinPoint) throws Throwable {
    //     System.out.println("4 :: Before authenticate method is called...");
    //     Object result = joinPoint.proceed(); 
    //     System.out.println("5 :: After authenticate method is called...");
    //     System.out.println("6 :: Response: " + result);
    //     return result;
    // }

    @AfterThrowing(pointcut = "execution(* base.pkg.auth.AuthenticationService.authenticate(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("AfterThrowing ::::: " + exception.getMessage());
    }

    @Around("execution(* base.pkg.auth.AuthenticationService.authenticate(..))")
    public Object aroundAuthenticate(ProceedingJoinPoint joinPoint) throws Throwable {

        // Accessing method signature
        Signature signature = joinPoint.getSignature();
        System.out.println("Method signature: " + signature);

        // Accessing method arguments
        Object[] args = joinPoint.getArgs();
        System.out.println("Method arguments: " + args);

        // Accessing target object
        Object target = joinPoint.getThis();
        System.out.println("Target object: " + target);

        // Accessing static part information
        String staticPartInfo = joinPoint.getStaticPart().toShortString();
        System.out.println("Static part information: " + staticPartInfo);

        try {
            System.out.println("4 :: Before authenticate method is called...");
            Object result = joinPoint.proceed();
            System.out.println("5 :: Response: " + result);
            return result;

        } catch (Exception e) {
            System.out.println("5 :: Exception occurred: " + e.getMessage());
            throw e; // Re-throw the exception to propagate it further
        }

    }

}
