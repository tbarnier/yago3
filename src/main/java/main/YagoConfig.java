package main;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Component
@PropertySource("classpath:yago.yml")
@ConfigurationProperties(prefix = "yago")
@Data
public class YagoConfig {

    /** Where the files shall go */
    private File outputFolder;

    @Setter(AccessLevel.NONE)
    private File yagoFolder;

    @Setter(AccessLevel.NONE)
    private File yagoSimulationFolder;

    /** Where the neo4j files shall go */
    private File neo4jFolder = null;

    // Ajout TB
    private boolean includeConcepts;

    /** TB: Regexp to rerun extractors */
    private List<String> rerunDependentOn;

    /** Wikipedias in different languages */
    private Map<String, File> wikipedias;
    //
    //    /** Extractors still to do */
    //    private List<Extractor> extractorsToDo;
    //
    //    /** Extractors running */
    //    private List<Extractor> extractorsRunning = new ArrayList<>();
    //
    //    /** Extractors running */
    //    private List<Extractor> extractorsFailed = new ArrayList<>();
    //
    //    /** Themes we have */
    //    private Set<Theme> themesWeHave = new TreeSet<>();
    //
    //    /** Themes we produced and that were not used */
    //    private Set<Theme> themesWeProducedAndNobodyConsumed = new TreeSet<>();
    //
    //    /** Caches we killed */
    //    private Set<Theme> cachesWeKilled = new TreeSet<>();

    private List<String> languages = Collections.EMPTY_LIST;

    /** Extractor names */
    private List<String> extractors = Collections.EMPTY_LIST;

    /** Number of threads we want */
    private int numThreads = 16;

    /** TRUE if we are just simulating a run */
    @Setter(AccessLevel.NONE)
    private boolean simulate = true;

    @Setter(AccessLevel.NONE)
    private boolean reuse = true;

    /**
     * TRUE if we run extractors which take a theme as an input, which was
     * regenerated
     */
    @Setter(AccessLevel.NONE)
    private boolean rerunDependentExtractors = false;
    //
    //    /** Maps from a theme to the extractor which produces it */
    //    private Map<Theme, Extractor> theme2extractor = new HashMap<>();
    //
    //    /** Maps from a follow-up theme to the base extractor */
    //    private Map<Extractor, List<Extractor>> baseExtractor2FollowUp = new HashMap<>();
    //
    //    private Map<String, List<Extractor>> call2extractor = new HashMap<>();
}
