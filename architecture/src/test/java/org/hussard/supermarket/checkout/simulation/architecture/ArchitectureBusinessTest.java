package org.hussard.supermarket.checkout.simulation.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 *
 */
@AnalyzeClasses(
        packages = "org.hussard.supermarket.checkout.simulation",
        importOptions = { ImportOption.DoNotIncludeTests.class})
public class ArchitectureBusinessTest {
    /**
     * Le module Business ne peut pas avoir de dépendances avec les deux autres modules application, infrastructure
     */
    @ArchTest
    public static final ArchRule business_layer_dependencies_are_respected = layeredArchitecture().consideringOnlyDependenciesInLayers()
            .withOptionalLayers(true)
            .layer("Business").definedBy("org.hussard.supermarket.checkout.simulation.business..")
            .layer("Application").definedBy("org.hussard.supermarket.checkout.simulation.application..")
            .layer("Infrastructure").definedBy("org.hussard.supermarket.checkout.simulation.infrastructure..")
            .whereLayer("Business").mayNotAccessAnyLayer()
            .because("Le module \'business\' ne doit pas avoir de dependence avec les autres modules");
}
