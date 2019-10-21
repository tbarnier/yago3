package utils.demonyms;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javatools.filehandlers.FileUtils;

/**
 * Helper functions/patterns to deal with Wikipedia
 * 
 * @author Thomas Rebele
 *
 */
public class DemonymPattern {

    public static Pattern simplePattern = Pattern.compile("[a-zA-ZéñÅ. ]*");

    public static Pattern paranthesisPattern = Pattern.compile("\\([^\\)]*\\)");

    public static Pattern quotePattern = Pattern.compile("''([^']*)''");

    public static Pattern wikiLink = Pattern.compile("\\[\\[(?<page>[^\\|\\]#]*)(#(?<section>[^\\|\\]]*))?(\\|(?<anchor>[^|\\]]*))?\\]\\]");

    public static Pattern refPattern = Pattern.compile(Pattern.quote("<ref") + "[^<]*" + Pattern.quote("</ref>"));

    public static Pattern bracesPattern = Pattern.compile(Pattern.quote("{{") + "[^}]*" + Pattern.quote("}}"));

    /**
     * Extract demonyms from wikipedia; creates a cache file in temporary folder
     * 
     * @param title
     * @param language
     * @return
     */
    public static String getPage(String title, String language) {
        // load file locally if exists
        String dir = "data/demonyms/";
        String filename = "wiki-" + language + "-" + title + ".txt";
        File cacheFile = new File(dir, filename);
        String content = null;
        if (cacheFile.exists()) {
            // load from cache
            try {
                content = FileUtils.getFileContent(cacheFile);
                return content;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

}
