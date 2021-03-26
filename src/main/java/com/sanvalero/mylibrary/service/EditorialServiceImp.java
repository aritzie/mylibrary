package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Editorial;
import com.sanvalero.mylibrary.exception.EditorialNotFoundException;
import com.sanvalero.mylibrary.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class EditorialServiceImp implements EditorialService {

    @Autowired
    EditorialRepository editorialRepository;

    @Override
    public Set<Editorial> findAllEditorials() {
        return editorialRepository.findAll();
    }

    @Override
    public Optional<Editorial> findById(long id) {
        return editorialRepository.findById(id);
    }

    @Override
    public Editorial addEditorial(Editorial editorial) {
        return editorialRepository.save(editorial);
    }

    @Override
    public Editorial modifyEditorial(long id, Editorial newEditorial) {
        Editorial editorial = findById(id).orElseThrow(()-> new EditorialNotFoundException(id));
        newEditorial.setId(editorial.getId());
        return editorialRepository.save(newEditorial);
    }

    @Override
    public void deleteEditorial(long id) {
        editorialRepository.findById(id).orElseThrow(()->new EditorialNotFoundException(id));
        editorialRepository.deleteById(id);
    }
}
