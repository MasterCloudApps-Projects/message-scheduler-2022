package es.urjc.tfm.scheduly;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum SchedulyFeatures implements Feature {
 
    @Label("Task Scheduler")
    SCHEDULE_MESSAGE_SCHEDULER;
    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
    
}