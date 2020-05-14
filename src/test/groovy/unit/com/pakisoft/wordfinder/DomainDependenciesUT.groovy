package com.pakisoft.wordfinder

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchUnitRunner
import com.tngtech.archunit.lang.ArchRule
import org.junit.runner.RunWith

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@RunWith(value = ArchUnitRunner)
@AnalyzeClasses(packages = 'com.pakisoft.wordfinder', importOptions = ImportOption.DoNotIncludeTests)
class DomainDependenciesUT {

    static DOMAIN = '..domain..'
    static SLF4J = 'org.slf4j..'
    static GUAVA = 'com.google.common..'
    static REFLECTIONS = 'org.reflections..'
    static JAVA = 'java..'

    @ArchTest
    static final ArchRule domain_should_not_depend_on_application_and_infrastructure =
            noClasses()
                    .that()
                    .resideInAPackage(DOMAIN)
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage('..infrastructure..')
                    .orShould()
                    .dependOnClassesThat()
                    .resideInAPackage('..application..')

    @ArchTest
    static final ArchRule domain_should_only_depend_on_itself_and_core_utils =
            classes()
                    .that()
                    .resideInAPackage(DOMAIN)
                    .should()
                    .onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            DOMAIN,
                            JAVA,
                            SLF4J,
                            GUAVA,
                            REFLECTIONS
                    )
}
