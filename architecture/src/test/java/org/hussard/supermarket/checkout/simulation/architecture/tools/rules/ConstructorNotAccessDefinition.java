package org.hussard.supermarket.checkout.simulation.architecture.tools.rules;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.apache.commons.collections4.CollectionUtils;

public class ConstructorNotAccessDefinition extends ArchCondition<JavaClass> {
    public ConstructorNotAccessDefinition() {
        super("not have define constructor access");
    }

    @Override
    public void check(JavaClass javaClass, ConditionEvents events) {
        javaClass.getConstructors().stream()
                .filter(javaConstructor -> isContainsConstructorAccess(javaConstructor))
                .forEach(item -> createViolation(javaClass, events));
    }

    private static boolean isContainsConstructorAccess(JavaConstructor javaConstructor) {
        return CollectionUtils.isNotEmpty(javaConstructor.getModifiers());
    }

    private static void createViolation(JavaClass javaClass, ConditionEvents events) {
        events.add(SimpleConditionEvent.violated(javaClass, String.format("%s %s Le contructeur ne doit pas avoir de définition d'accès(pas public, pas private)",
        javaClass.getName(), javaClass.getSourceCodeLocation())));
    }
}
