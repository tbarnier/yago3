/*
This class is part of the YAGO project at the Max Planck Institute
for Informatics/Germany and Télécom ParisTech University/France:
http://yago-knowledge.org

This class is copyright 2016 Mohamed Amir Yosef, with contributions
from Johannes Hoffart.

YAGO is free software: you can redistribute it and/or modify it
under the terms of the GNU General Public License as published
by the Free Software Foundation, either version 3 of the License,
or (at your option) any later version.

YAGO is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
License for more details.

You should have received a copy of the GNU General Public License
along with YAGO.  If not, see <http://www.gnu.org/licenses/>.
*/

package deduplicators;

import basics.Fact;
import basics.RDFS;
import basics.YAGO;
import extractors.Extractor;
import extractors.MultilingualExtractor;
import fromOtherSources.*;
import fromThemes.CategoryConteXtExtractor;
import fromThemes.TransitiveTypeSubgraphExtractor;
import fromWikipedia.*;
import javatools.administrative.Announce;
import javatools.datatypes.FinalSet;
import utils.Theme;
import utils.Theme.ThemeGroup;

import java.util.HashSet;
import java.util.Set;

/**
*/

public class AIDAExtractorMerger extends Extractor {

    /** All facts of YAGO */
    public static final Theme AIDAFACTS = new Theme("aidaFacts", "All facts necessary for AIDA", ThemeGroup.OTHER);

    /** Relations that AIDA needs. */
    public static final Set<String> relations = new FinalSet<>(RDFS.type, RDFS.subclassOf, RDFS.label, YAGO.hasPreferredName, RDFS.sameas,
            "<hasGivenName>", "<hasFamilyName>", "<hasGender>", "<hasAnchorText>", "<hasInternalWikipediaLinkTo>", "<redirectedFrom>",
            "<hasWikipediaUrl>", "<hasCitationTitle>", "<hasWikipediaCategory>", "<hasWikipediaAnchorText>", "<_hasTranslation>", "<hasWikipediaId>",
            "<_yagoMetadata>", YAGO.hasImageID, YAGO.hasWikiPage, YAGO.hasImageUrl, YAGO.hasGloss, YAGO.hasLicense, YAGO.hasAuthor, YAGO.hasTrademark,
            YAGO.hasName, YAGO.hasUrl, YAGO.hasOTRSId, YAGO.hasShortDescription, YAGO.hasLongDescription, YAGO.isNamedEntity, "<_hasLinkLikelihood>");

    @Override
    public Set<Theme> input() {
        Set<Theme> input = new HashSet<>();

        //YAGO functional facts needed for AIDA
        //hasWIkipediaUrl, hasGender
        //hasGivenName, hasFamilyName
        //isNamedEntity
        input.add(AIDAFunctionalExtractor.AIDAFUNCTIONALFACTS);

        //the rest of the facts that don't need functional check
        // Dictionary.
        input.addAll(StructureExtractor.STRUCTUREFACTS.inLanguages(MultilingualExtractor.wikipediaLanguages)); // also gives links and anchor texts.
        input.addAll(DisambiguationPageExtractor.DISAMBIGUATIONMEANSFACTS.inLanguages(MultilingualExtractor.wikipediaLanguages));
        input.addAll(RedirectExtractor.REDIRECTFACTS.inLanguages(MultilingualExtractor.wikipediaLanguages));
        input.add(HardExtractor.HARDWIREDFACTS);

        // Types and Taxonomy.
        input.add(TransitiveTypeSubgraphExtractor.YAGOTRANSITIVETYPE);
        input.add(ClassExtractor.YAGOTAXONOMY);

        // Keyphrases.
        input.addAll(ConteXtExtractor.CONTEXTFACTS.inLanguages(MultilingualExtractor.wikipediaLanguages));
        input.add(CategoryConteXtExtractor.CATEGORY_CONTEXT.inEnglish());
        input.addAll(CategoryConteXtExtractor.CATEGORY_CONTEXT_ENTITIES_TRANSLATED.inLanguages(MultilingualExtractor.allLanguagesExceptEnglish()));

        // Translation.
        input.addAll(DictionaryExtractor.ENTITY_DICTIONARY.inLanguages(MultilingualExtractor.allLanguagesExceptEnglish()));
        input.addAll(DictionaryExtractor.CATEGORY_DICTIONARY.inLanguages(MultilingualExtractor.allLanguagesExceptEnglish()));

        // Metadata.
        input.add(MetadataExtractor.METADATAFACTS);
        input.addAll(WikiInfoExtractor.WIKIINFO.inLanguages(MultilingualExtractor.wikipediaLanguages));

        // Image.
        input.add(WikidataImageExtractor.WIKIDATAIMAGES);

        // WikiData links.
        input.add(WikidataLabelExtractor.WIKIDATAINSTANCES);

        // Wikipedie category glosses.
        input.addAll(CategoryGlossExtractor.CATEGORYGLOSSES.inLanguages(MultilingualExtractor.wikipediaLanguages));

        // Image Licenses.
        input.add(WikidataImageLicenseExtractor.WIKIDATAIMAGELICENSE);

        // Entity descriptions.
        input.add(WikidataEntityDescriptionExtractor.WIKIDATAENTITYDESCRIPTIONS);
        input.addAll(WikipediaEntityDescriptionExtractor.WIKIPEDIAENTITYDESCRIPTIONS.inLanguages(MultilingualExtractor.wikipediaLanguages));

        // Mention Link Likelihood
        input.addAll(MentionLinkLikelihoodExtractor.LIKELIHOODFACTS.inLanguages(MultilingualExtractor.wikipediaLanguages));

        return input;
    }

    @Override
    public Set<Theme> output() {
        return new FinalSet<>(AIDAFACTS);
    }

    @Override
    public void extract() throws Exception {
        Announce.doing("Merging all AIDA Sources");
        for (Theme theme : input()) {
            Announce.doing("Merging facts from", theme);
            for (Fact fact : theme) {
                if (isAIDARelation(fact)) {
                    AIDAFACTS.write(fact);
                }
            }
            Announce.done();
        }
        Announce.done();
    }

    public boolean isAIDARelation(Fact fact) {
        return relations.contains(fact.getRelation());
    }

}
