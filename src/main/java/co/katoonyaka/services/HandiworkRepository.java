package co.katoonyaka.services;

import co.katoonyaka.domain.Handiwork;

public interface HandiworkRepository extends RepositoryBase<Handiwork> {

    Handiwork findByUrl(String url);

}
