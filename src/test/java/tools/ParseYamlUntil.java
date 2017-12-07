package tools;

import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by P0061799 on 2017/12/1.
 */
public class ParseYamlUntil {
    public String yamlFile;
    public HashMap<String, HashMap<String, String>> ml;
    private HashMap<String, HashMap<String, String>> extendLocator;

    public ParseYamlUntil(String yamlFileName){
        this.yamlFile=yamlFileName;
        this.getYamlFile();
    }

    protected void getYamlFile() {
        File f = new File("src/main/resources/" + this.yamlFile + ".yaml");

        try {
            this.ml = (HashMap) Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
        } catch (FileNotFoundException var3) {
            var3.printStackTrace();
        }

    }

    public void loadExtendLocator(String fileName) {
        File f = new File("locator/" + fileName + ".yaml");

        try {
            this.extendLocator = (HashMap)Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
            this.ml.putAll(this.extendLocator);
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        }

    }

    public void setLocatorVariableValue(String variable, String value) {
        Set<String> keys = this.ml.keySet();
        Iterator var4 = keys.iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            String v = ((String)((HashMap)this.ml.get(key)).get("value")).replaceAll("%" + variable + "%", value);
            ((HashMap)this.ml.get(key)).put("value", v);
        }

    }
}
