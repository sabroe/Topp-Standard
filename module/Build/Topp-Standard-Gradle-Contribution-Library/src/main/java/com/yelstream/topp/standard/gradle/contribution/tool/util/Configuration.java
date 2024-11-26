package com.yelstream.topp.standard.gradle.contribution.tool.util;

import lombok.experimental.UtilityClass;

/**
 * Utilities addressing configuration by Gradle properties.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-11-26
 */
@UtilityClass
public class Configuration {

    public static boolean isTrue() {

/*
    def enableFeatureText = custom['feature.slf4j.enable']?.toString()?.trim()
    def enableFeature = enableFeatureText ? enableFeatureText.toBoolean() : true
    if (!enableFeature) {
        logger.debug("Feature 'SLF4J' is disabled.")
    } else {
 */

        return false;
    }

    public static String resolveFirst() {
        return null;
    }

    public static /*JavaVersion*/ String xxx() {

/*
    java {
        def sourceJavaVersionString = (custom['java.language-version.source'] as String)?.trim()
        def targetJavaVersionString = (custom['java.language-version.target'] as String)?.trim()
        def toolChainJavaVersionString = (custom['java.language-version.tool-chain'] as String)?.trim()
        def javaVersionString = (custom['java.language-version'] as String)?.trim()

        def resolvedLanguageVersion = null
        if (toolChainJavaVersionString && toolChainJavaVersionString != "*" && toolChainJavaVersionString != "!") {
            resolvedLanguageVersion = JavaLanguageVersion.of(Integer.parseInt(toolChainJavaVersionString))
        } else if (toolChainJavaVersionString == "!")  {
            def defaultJavaVersion = javaVersionString ? javaVersionString : JavaVersion.VERSION_21.majorVersion
            resolvedLanguageVersion = JavaLanguageVersion.of(Integer.parseInt(defaultJavaVersion))
        }

        sourceCompatibility = sourceJavaVersionString ? JavaVersion.toVersion(sourceJavaVersionString) : (javaVersionString ? JavaVersion.toVersion(javaVersionString) : JavaVersion.VERSION_21)
        targetCompatibility = targetJavaVersionString ? JavaVersion.toVersion(targetJavaVersionString) : (javaVersionString ? JavaVersion.toVersion(javaVersionString) : JavaVersion.VERSION_21)

        toolchain {
            if (resolvedLanguageVersion != null) {
                languageVersion = resolvedLanguageVersion
            }
        }
    }
 */

        return null;
    }

}
