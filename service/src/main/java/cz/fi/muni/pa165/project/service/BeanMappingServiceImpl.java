package cz.fi.muni.pa165.project.service;


import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Petr Hendrych
 * @created 28.04.2021
 * @project airport-manager
 **/

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper mapper;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(mapper.map(object, mapToClass));
        }
        return mappedCollection;

    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return mapper.map(u, mapToClass);
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }
}
