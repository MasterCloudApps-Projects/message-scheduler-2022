package es.urjc.tfm.scheduly;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.Feature;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

@Configuration
public class ToggleConfiguration implements TogglzConfig {


    @Bean
    public StateRepository getStateRepository() {
        return new InMemoryStateRepository();
    }

    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(SchedulyFeatures.class);
    }

    
    @Override
    public UserProvider getUserProvider() {
        return new UserProvider() {
            @Override
            public FeatureUser getCurrentUser() {
                return new SimpleFeatureUser("admin", true);
            }
        };
    }

    
    @Bean
    public FeatureManager featureManager(StateRepository stateRepository) {
        return new FeatureManagerBuilder()
            .featureEnum(SchedulyFeatures.class)
            .stateRepository(stateRepository)
            .build();
    }

	@Override
	public Class<? extends Feature> getFeatureClass() {
		return SchedulyFeatures.class;
	}
}
