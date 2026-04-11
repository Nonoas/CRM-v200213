package de.felixroske.jfxsupport;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleControl extends ResourceBundle.Control {

    private final Charset charset;

    public ResourceBundleControl(Charset charset) {
        this.charset = charset;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
        throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                stream = connection.getInputStream();
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream == null) {
            return null;
        }
        try (InputStream inputStream = stream; Reader reader = new InputStreamReader(inputStream, charset)) {
            return new PropertyResourceBundle(reader);
        }
    }
}
