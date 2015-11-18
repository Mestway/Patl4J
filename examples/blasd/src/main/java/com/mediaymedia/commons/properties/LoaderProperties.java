package com.mediaymedia.commons.properties;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Enumeration;
import java.io.InputStream;

/**
 *
 * User: juan
 * Date: 04-sep-2007
 * Time: 11:51:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class LoaderProperties {

    public static Properties load(String name, ClassLoader loader)
    {
        if (name == null)
            throw new IllegalArgumentException ("null input: name");

        if (name.startsWith ("/"))
            name = name.substring (1);

        if (name.endsWith (SUFFIX))
            name = name.substring (0, name.length () - SUFFIX.length ());

        Properties result = null;

        InputStream in = null;
        try
        {
            if (loader == null) loader = ClassLoader.getSystemClassLoader ();

            if (LOAD_AS_RESOURCE_BUNDLE)
            {
                name = name.replace ('/', '.');
                // Throws MissingResourceException on lookup failures:
                final ResourceBundle rb = ResourceBundle.getBundle (name,
                    Locale.getDefault (), loader);

                result = new Properties ();
                for (Enumeration keys = rb.getKeys (); keys.hasMoreElements ();)
                {
                    final String key = (String) keys.nextElement ();
                    final String value = rb.getString (key);

                    result.put (key, value);
                }
            }
            else
            {
                name = name.replace ('.', '/');

                if (! name.endsWith (SUFFIX))
                    name = name.concat (SUFFIX);

                // Returns null on lookup failures:
                in = loader.getResourceAsStream (name);
                if (in != null)
                {
                    result = new Properties ();
                    result.load (in); // Can throw IOException
                }
            }
        }
        catch (Exception e)
        {
            result = null;
        }
        finally
        {
            if (in != null) try { in.close (); } catch (Throwable ignore) {}
        }

        if (THROW_ON_LOAD_FAILURE && (result == null))
        {
            throw new IllegalArgumentException ("could not load [" + name + "]"+
                " as " + (LOAD_AS_RESOURCE_BUNDLE
                ? "a resource bundle"
                : "a classloader resource"));
        }

        return result;
    }

    /**
     * A convenience overload of {@link #load(String, ClassLoader)}
     * that uses the current thread's context classloader.
     */
    public static Properties load(final String name)
    {
        return load(name,
            Thread.currentThread ().getContextClassLoader ());
    }

    private static final boolean THROW_ON_LOAD_FAILURE = true;
    private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
    private static final String SUFFIX = ".properties";
}
