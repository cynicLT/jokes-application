package org.cynic.jokes.architecture;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.cynic.jokes.Constants;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;


@AnalyzeClasses(importOptions = ImportOption.DoNotIncludeTests.class, packagesOf = Constants.class)
public class ApplicationTest {

    @ArchTest
    public static final ArchRule ALL_CONTROLLERS_MUST_BE_IN_PACKAGE =
            ArchRuleDefinition.classes()
                    .that().areAnnotatedWith(RestController.class)
                    .should().resideInAPackage("..controller..")
                    .because("API endpoints  must be [controller] package");


    @ArchTest
    public static final ArchRule CONSTANTS_ARE_FINAL =
            ArchRuleDefinition.theClass(Constants.class)
                    .should().haveOnlyFinalFields()
                    .andShould().bePublic()
                    .andShould().notBeAnnotatedWith(Component.class)
                    .andShould().notBeAnnotatedWith(Service.class)
                    .because("Constants are not Business Logic");

    @ArchTest
    public static final ArchRule CONSTANTS_HAS_PRIVATE_CONSTRUCTOR =
            ArchRuleDefinition.constructors()
                    .that().areDeclaredIn(Constants.class)
                    .should().haveModifier(JavaModifier.PRIVATE)
                    .because("Constants are singletons");

    @ArchTest
    public static final ArchRule ALL_RECORDS_SERIALIZABLE =
            ArchRuleDefinition.classes()
                    .that().resideInAPackage("..domain.http..")
                    .should().implement(Serializable.class)
                    .because("There is requirement from Hazelcast on fetching relations to be serializable");


    @ArchTest
    public static final ArchRule NO_CYCLIC_DEPS = SlicesRuleDefinition.slices()
            .matching("org.cynic.jokes.(*)..").should().beFreeOfCycles();
}
