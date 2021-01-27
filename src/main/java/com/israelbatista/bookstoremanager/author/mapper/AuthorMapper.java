package com.israelbatista.bookstoremanager.author.mapper;

import com.israelbatista.bookstoremanager.author.dto.AuthorDTO;
import com.israelbatista.bookstoremanager.author.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);



}
