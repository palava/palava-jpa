package de.cosmocode.palava.model.base;

import de.cosmocode.json.JSONMapable;
import de.cosmocode.json.JSONRenderer;

/**
 * Abstract base implementation of the {@link LocationBase} interface
 * which provides meaningful {@link JSONMapable#renderAsMap(JSONRenderer)} method.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractLocation implements LocationBase {

    @Override
    public JSONRenderer renderAsMap(JSONRenderer renderer) {
        return renderer.
            key("latitude").value(getLatitude()).
            key("longitude").value(getLongitude());
    }

}
