package org.hussard.supermarket.checkout.simulation.architecture.tools.rules;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class DefaultConstructorDefinition extends ArchCondition<JavaClass> {
    public DefaultConstructorDefinition() {
        super("only have a default constructor");
    }
    @Override
    public void check(JavaClass javaClass, ConditionEvents events) {
        if(javaClass.getConstructors().size() != 1) {
            events.add(SimpleConditionEvent.violated(javaClass, String.format("%s does not have only 1 constructor %s",
                    javaClass.getName(),javaClass.getSourceCodeLocation())));
        }
    }
}
