package org.hussard.supermarket.checkout.simulation.architecture.tools;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.core.domain.JavaFieldAccess;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.hussard.supermarket.checkout.simulation.architecture.tools.rules.ConstructorNotAccessDefinition;
import org.hussard.supermarket.checkout.simulation.architecture.tools.rules.DefaultConstructorDefinition;
import org.hussard.supermarket.checkout.simulation.architecture.tools.rules.FieldPrivateFinalAccess;

import java.text.MessageFormat;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Definition des règles sur l'écriture d'une classe modèle Immutable.
 * @author hussard
 * @version 0.0.1
 * </p>
 * <p>
 * Les règles sont les suivantes :
 * <ul>
 * <li>Le contructeur ne doit pas avoir de qualificateur :
 * <p>
 *     il ne sera donc pas possible de faire un new XXX sans passer par un pattern builder ou pattern builder fluent( de préférence )
 * </p>
 * </li>
 * <li>Toutes les propriètès doivent être private final :
 * <p>
 * Elles sont 'private final' donc accessible en lecture sans passer par un getXXX
 * </p>
 * </li>
 * <li>il est interdit d'avoir de setter : Pourquoi de getter ?
 * <p>Tous simplement si l'objet est immutable pourquoi avoir un getter
 * exemple :
 *  - personne.getName();
 *  - personne.name();
 *  Dans les deux cas, on veut le nom mais pourquoi ajouter 'get'
 *  </p>
 *  </li>
 * </ul> </p>
 */
public class ArchConditionImmutableCoding {
    public static ArchCondition<JavaClass> validate(){
        return new ArchCondition<JavaClass>("only have a default constructor") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                Set<JavaConstructor> constructors = javaClass.getConstructors();
                if (constructors.size() != 1) {
                    events.add(SimpleConditionEvent.violated(javaClass,
                                                             String.format("%s does not have only 1 constructor", javaClass.getName())));
                    return;
                }

                JavaConstructor constructor = constructors.iterator().next();
                if (!constructor.getThrowsClause().getTypes().isEmpty()) {
                    events.add(SimpleConditionEvent.violated(javaClass,
                                                             String.format("%s has a throws clause %s",
                                                                           constructor.getFullName(), constructor.getSourceCodeLocation())));
                    return;
                }

                JavaModifier constructorAccessModifier = null, classAccessModifier = null;
                for (JavaModifier modifier : asList(JavaModifier.PUBLIC, JavaModifier.PROTECTED, JavaModifier.PRIVATE)) {
                    if (constructor.getModifiers().contains(modifier)) {
                        constructorAccessModifier = modifier;
                    }
                    if (javaClass.getModifiers().contains(modifier)) {
                        classAccessModifier = modifier;
                    }
                }
                if (classAccessModifier != constructorAccessModifier) {
                    events.add(SimpleConditionEvent.violated(javaClass,
                                                             String.format("%s has another access modifier than its class %s",
                                                                           constructor.getFullName(), constructor.getSourceCodeLocation())));
                    return;
                }

                int expectedNumberOfParameters = javaClass.isInnerClass() ? 1 : 0;
                if (constructor.getRawParameterTypes().size() != expectedNumberOfParameters) {
                    events.add(SimpleConditionEvent.violated(javaClass,
                                                             String.format("%s does not have %d parameters %s",
                                                                           constructor.getFullName(), expectedNumberOfParameters, constructor.getSourceCodeLocation())));
                    return;
                }

                events.add(SimpleConditionEvent.satisfied(javaClass,
                                                          String.format("%s seems to have a default constructor %s",
                                                                        javaClass.getName(), constructor.getSourceCodeLocation())));
            }
        };
    }
    /**
     * Condition qui contrôle si le constructeur à une définition d'accès
     *
     * @return ArchCondition<JavaClass>
     */
    public static ArchCondition<JavaClass> defaultConstructorDefinition() {
        return new DefaultConstructorDefinition();
    }
    /**
     * Condition qui contrôle si le constructeur à une définition d'accès
     *
     * @return ArchCondition<JavaClass>
     */
    public static ArchCondition<JavaClass> contructorNotAccessDefinition() {
        return new ConstructorNotAccessDefinition();
    }
    /**
     * Controle l'absence de setter
     *
     * @return ArchCondition<JavaClass>
     */
    public static ArchCondition<JavaClass> noSetterAccess() {
        return new ArchCondition<>("not have define setter access") {

            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                boolean satisfied = javaClass.getFields().stream().anyMatch(field -> field.getAccessesToSelf().stream()
                                                                                          .noneMatch(
                                                                                                  it -> it.getAccessType() == JavaFieldAccess.AccessType.SET));
                String message = MessageFormat.format("{0} setter interdit", javaClass.getDescription());
                events.add(new SimpleConditionEvent(javaClass, satisfied, message));
            }
        };
    }

    /**
     * Controle l'absence de getter
     *
     * @return ArchCondition<JavaClass>
     */
    public static ArchCondition<JavaClass> noGetterAccess() {
        return new ArchCondition<>("not have define getter access") {

            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                boolean satisfied = javaClass.getFields().stream().anyMatch(field -> field.getAccessesToSelf().stream()
                                                                                          .noneMatch(
                                                                                                  it -> it.getAccessType() == JavaFieldAccess.AccessType.GET));
                String message = MessageFormat.format("{0} getter interdit", javaClass.getDescription());
                events.add(new SimpleConditionEvent(javaClass, satisfied, message));
            }
        };
    }
    /**
     * Controle l'absence de getter
     *
     * @return ArchCondition<JavaClass>
     */
    public static ArchCondition<JavaClass> isPrivateFinalAccess() {
        return new FieldPrivateFinalAccess();
    }

}
