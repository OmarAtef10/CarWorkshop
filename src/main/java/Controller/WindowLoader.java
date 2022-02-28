package Controller;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import Context.Context;
import View.AddStockShelveWindow;
import View.LoginWindow;
import View.MainWindow;
import View.ProductWindow;
import javafx.fxml.FXMLLoader;

public class WindowLoader {
    private static HashMap<String, FXMLLoader> loaders;
    private static Locale currLocale = Context.getContext().getCurrLocale();


    public static void initLoaders(){
        //register new windows here
        //we do this so the app isn't slow, we load all windows when the app starts then we get the loader
        //isntead of creating a new loader each time we want to see the window
        loaders = new HashMap<String, FXMLLoader>(){{
            put(MainWindow.FXML_NAME, createLoader(MainWindow.FXML_NAME, currLocale));
            put(ProductWindow.FXML_NAME, createLoader(ProductWindow.FXML_NAME, currLocale));
            put(LoginWindow.FXML_NAME, createLoader(LoginWindow.FXML_NAME, currLocale));
            put(AddStockShelveWindow.FXML_NAME, createLoader(AddStockShelveWindow.FXML_NAME, currLocale));
        }};
    }

    public static FXMLLoader getLoader(String fxml){
        FXMLLoader loader = loaders.get(fxml);
        if(loader != null){
            loader.setRoot(null);
            loader.setController(null);   
            return loader;
        }
        System.out.println("WARNING: Loader of " + fxml + " was not found");
        loader = createLoader(fxml, currLocale);
        loaders.put(fxml, loader);
        return loader;
    }


    private static FXMLLoader createLoader(String fxml, Locale locale){
        FXMLLoader loader = new FXMLLoader();
        try{
            File fxmlFile = new File("src/main/resources/view/"+fxml);
            ResourceBundle resource = ResourceBundle.getBundle("i18n.Locale", locale);
            loader.setLocation(fxmlFile.toURI().toURL());
            loader.setResources(resource);
        }catch(Exception e){
            e.printStackTrace();
        }
        return loader;
    }
}
