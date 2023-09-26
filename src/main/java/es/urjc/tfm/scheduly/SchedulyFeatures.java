package es.urjc.tfm.scheduly;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum SchedulyFeatures implements Feature {
 
    @Label("Using MessageUseCase instead of MessageRepository")
    MESSAGE_USE_CASE;
    
    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
    
}