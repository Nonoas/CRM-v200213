package de.felixroske.jfxsupport;

import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class PropertyReaderHelper {

    private PropertyReaderHelper() {
    }

    public static String determineFilePathFromPackageName(Class<?> type) {
        Package typePackage = type.getPackage();
        if (typePackage == null) {
            return "/";
        }
        return "/" + typePackage.getName().replace('.', '/') + "/";
    }

    public static String determineBundleName(String conventionalName) {
        return conventionalName;
    }

    public static List<String> get(Environment environment, String propertyName) {
        String value = environment.getProperty(propertyName);
        if (!StringUtils.hasText(value)) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(","))
            .map(String::trim)
            .filter(StringUtils::hasText)
            .collect(Collectors.toList());
    }
}
