package database.mmasenheimerdbex.database.mappers.impl;

import database.mmasenheimerdbex.database.domain.dto.AuthorDto;
import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;
import database.mmasenheimerdbex.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        // Accessing the model mapper via constructor injection
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
        // Map to an AuthorDto object
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
        // Map to an AuthorEntity object
    }
}