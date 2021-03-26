package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Editorial;

import java.util.Optional;
import java.util.Set;

public interface EditorialService {

    Set<Editorial> findAllEditorials();
    Optional<Editorial> findById(long id);
    Editorial addEditorial(Editorial editorial);
    Editorial modifyEditorial(long id, Editorial editorial);
    void deleteEditorial(long id);
}
