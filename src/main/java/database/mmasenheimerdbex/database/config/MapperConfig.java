package database.mmasenheimerdbex.database.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// This spring class provides beans

public class MapperConfig {

    @Bean
    // This class allows for automatic mapping between DTOS and entities
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        // Mapping nested objects and allows us to map from one object to another
        return modelMapper;

    }
}
