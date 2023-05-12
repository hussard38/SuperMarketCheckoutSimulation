package org.hussard.supermarket.checkout.simulation.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.hussard.supermarket.checkout.simulation.architecture.tools.ArchConditionImmutableCoding;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "org.hussard.supermarket.checkout.simulation",
        importOptions = { ImportOption.DoNotIncludeTests.class})
public class CodingRulesBusinessTest {

    @ArchTest
    /*public static final ArchRule coding_rules_business_model = classes()
            .that().resideInAPackage("org.hussard.supermarket.checkout.simulation.business.model..")
            .should(ArchConditionImmutableCoding.contructorNotAccessDefinition())
            .andShould(ArchConditionImmutableCoding.defaultConstructorDefinition())
            .andShould(ArchConditionImmutableCoding.isPrivateFinalAccess())
            .andShould(ArchConditionImmutableCoding.noSetterAccess())
            .andShould(ArchConditionImmutableCoding.noGetterAccess()); @ArchTest*/
    public static final ArchRule coding_rules_business_model = classes()
            .that().resideInAPackage("org.hussard.supermarket.checkout.simulation.business.model..")
            .should(ArchConditionImmutableCoding.defaultConstructorDefinition())
            .andShould(ArchConditionImmutableCoding.contructorNotAccessDefinition())
            .andShould(ArchConditionImmutableCoding.isPrivateFinalAccess())
            ;

}
