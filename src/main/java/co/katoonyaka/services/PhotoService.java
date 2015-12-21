package co.katoonyaka.services;

import co.katoonyaka.domain.admin.PhotoUsageStatistics;

import java.util.Collection;

public interface PhotoService {

    PhotoUsageStatistics getPhotoUsageStatistics();

    void removeExternalResourcesByIds(Collection<String> externalIds);

}
