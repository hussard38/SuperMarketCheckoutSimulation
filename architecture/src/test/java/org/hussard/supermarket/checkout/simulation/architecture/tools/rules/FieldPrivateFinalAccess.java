package org.hussard.supermarket.checkout.simulation.architecture.tools.rules;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.util.List;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

/**
 * @author hussard
 * @version 0.0.1
 * <p/>
 * the field rule is uniquely 'private final'
 */
public class FieldPrivateFinalAccess extends ArchCondition<JavaClass> {
    public FieldPrivateFinalAccess() {
        super("the field  is uniquely 'private final'");
    }


    @Override
    public void check(JavaClass javaClass, ConditionEvents events) {
        List<JavaModifier> accessModifiers = List.of(JavaModifier.PRIVATE, JavaModifier.FINAL);
        javaClass.getAllFields().stream().distinct().filter(
                not(selectPrivateFinalFieldAccess(accessModifiers)))
                .forEach(
                         javaFieldAccess -> createViolation(javaClass, events, javaFieldAccess)
                 );
    }

    private static void createViolation(JavaClass javaClass, ConditionEvents events, JavaField javaFieldAccess) {
        String message = String.format("%s the class contain any or more fields not 'private final' %s",
                                       javaFieldAccess.getOwner().getSimpleName(), javaFieldAccess.getSourceCodeLocation());
        events.add(SimpleConditionEvent.violated(javaClass,message));
    }

    private static Predicate<JavaField> selectPrivateFinalFieldAccess(List<JavaModifier> accessModifiers) {
        return field -> field.getModifiers().containsAll(accessModifiers);
    }
}
