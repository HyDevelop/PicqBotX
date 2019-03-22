package cc.moecraft.test.general;

import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class GuavaDetector
{
    public static void main(String... args) throws IOException
    {
        System.out.println(determineGuavaMavenVersion());
        System.out.println(determineGuavaManifestVersion());
    }

    public static String determineGuavaMavenVersion() throws IOException
    {
        String resourceName = "META-INF/maven/com.google.guava/guava/pom.properties";
        Properties properties = new Properties();
        try (InputStream inputStream = Resources.getResource(resourceName).openStream())
        {
            properties.load(inputStream);
        }
        return properties.getProperty("version");
    }

    public static String determineGuavaManifestVersion() throws IOException
    {
        Enumeration<URL> resources = Resources.class.getClassLoader()
                .getResources("META-INF/MANIFEST.MF");
        while (resources.hasMoreElements())
        {
            Manifest manifest;
            try (InputStream inputStream = resources.nextElement().openStream())
            {
                manifest = new Manifest(inputStream);
            }
            Attributes mainAttributes = manifest.getMainAttributes();
            String symbolicName = mainAttributes.getValue("Bundle-SymbolicName");
            if ("com.google.guava".equals(symbolicName))
            {
                return mainAttributes.getValue("Bundle-Version");
            }
        }
        return null;
    }
}