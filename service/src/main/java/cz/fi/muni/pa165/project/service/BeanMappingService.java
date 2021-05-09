package cz.fi.muni.pa165.project.service;

import com.github.dozermapper.core.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Petr Hendrych, Jozef Vanický, Michal Zelenák
 * @created 28.04.2021
 * @project airport-manager
 **/

public interface BeanMappingService {

    /**
     * Mapping list of objects to class.
     * @param objects - Collection of objects to be mapped to different class.
     * @param mapToClass - Class to which objects are mapped.
     * @param <T> - Generic type.
     * @return - Returns list of typed objects.
     */
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Mapping object to class.
     * @param u - Object to be mapped to different class.
     * @param mapToClass - Class to which object is mapped.
     * @param <T> - Generic type.
     * @return - Returns typed object.
     */
    <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * @return - Returns dozer bean mapper.
     */
    Mapper getMapper();
}
