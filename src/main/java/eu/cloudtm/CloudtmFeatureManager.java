package eu.cloudtm;

import org.infinispan.dataplacement.c50.keyfeature.Feature;
import org.infinispan.dataplacement.c50.keyfeature.FeatureValue;
import org.infinispan.dataplacement.c50.keyfeature.KeyFeatureManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * // TODO: Document this
 *
 * @author Pedro Ruivo
 * @since 1.0
 */
public abstract class CloudtmFeatureManager implements KeyFeatureManager {

   @Override
   public Map<Feature, FeatureValue> getFeatures(Object key) {
      if (!(key instanceof String)) {
         throw new IllegalArgumentException("Expected a String");
      }
      //obter os hints da key
      String[] decoded = StringUtils.decode((String) key);
      if (decoded.length > 1) {
         Map<Feature, FeatureValue> featureValueMap = new HashMap<Feature, FeatureValue>();
         LocalityHints hints = LocalityHints.string2Hints(decoded[1]);
         for (Feature feature : getAllKeyFeatures()) {
            String value = hints.get(feature.getName());
            if (value != null) {
               featureValueMap.put(feature, feature.featureValueFromParser(value));
            }
         }
         return featureValueMap;
      }
      return Collections.emptyMap();
   }
   
   
}
