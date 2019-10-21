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

    private List<String> languages = Collections.emptyList();

    /** Extractor names */
    private List<String> extractors = Collections.emptyList();

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
 
}
