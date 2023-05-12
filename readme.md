# Supermarket checkout simulation
Pour donner un exemple de projet utilisant la structure hexagonale, nous allons faire la simulation d'une caisse de supermarché.

# Création du projet
Nous allons créer un projet maven de type **POM**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.hussard.supermarket.checkout.simulation</groupId>
    <artifactId>SuperMarketCheckoutSimulation</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

</project>
```
puis nous allons ajouter les autres modules même si dans un premier temps les deux modules **application** et **archetecture** ne seront pas la priorité
du coup, notre projet est composé des modules suivants :
* application
* business
* infrastructure
* architecture, ce module va nous servir à contrôle les bonnes pratiques de développement

## Définition des règles
La définition des règles entre les modules sera contrôlées avec [archunit](https://www.archunit.org/) .
Il fonctionne sur le même principe des tests-unitaires

* business
  * Le module Business ne doit pas avoir de dépendence avec les autres modules : [business_layer_dependencies_are_respected](https://github.com/hussard38/SuperMarketCheckoutSimulation/blob/cdcc6d9c3ac55ce89d6ee7dc61443cdf7741e42d/architecture/src/test/java/org/hussard/supermarket/checkout/simulation/architecture/ArchitectureBusinessTest.java#L19)

# Ressources
## Archunit
[Unit Test Your Java Architecture With ArchUnit by Roland Weisleder](https://www.youtube.com/watch?v=ef0lUToWxI8)
[source](https://github.com/rweisleder/ArchUnit)

