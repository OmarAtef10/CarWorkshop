package Context;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class Context {
    private static HashMap<String, Locale> availableLocales = new HashMap<String, Locale>(){{
        put("ar", new Locale("ar"));
        put("en", new Locale("en"));
    }};

    private static HashMap<String, String[]> themes = new HashMap<String, String[]>(){{
        put("light", new String[]{"/themes/base.css", "/themes/light_theme.css"});
        put("dark", new String[]{"/themes/base.css", "/themes/dark_theme.css"});
    }};

    private static Context context = null;

    private static Locale defaultLocale = availableLocales.get("en");

    private Locale currLocale = defaultLocale;

    private static String[] defaultTheme = themes.get("dark");

    private String[] currTheme = defaultTheme;

    private ResourceBundle localBundle;
    
    public void setLocale(Locale locale){
        Locale locale_ = availableLocales.get(locale.getLanguage());
        if(locale_ != null){
            currLocale = locale;
        }
        else{
            currLocale = defaultLocale;
            System.out.println("WARNING: LOCALE NOT FOUND");
        }
    }

    public Locale getCurrLocale(){
        return currLocale;
    }

    public void setCurrentTheme(String theme){
        String[] files = themes.get(theme);
        if(files != null){
            currTheme = files;
        }
        else{
            currTheme = defaultTheme;
            System.out.println("WARNING: THEME NOT FOUND");
        }
    }

    public String[] getCurrentTheme(){
        return currTheme;
    }
    
    private Context(){
        localBundle = ResourceBundle.getBundle("i18n.Locale", currLocale);
    }

    public static Context getContext(){
        if(context == null)
            context = new Context();
        return context;
    }

    /**
     * Get the string that is represented by given tag
     * @param tag A tag that must exist in all locale.properties files
     * @return String - Value of given tag or the given tag
     * @throws MissingResourceException if the value of tag isn't found
     */
    public String getString(String tag) {
        return localBundle.getString(tag);
    }
}
