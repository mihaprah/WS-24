package si.um.feri.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import si.um.feri.vao.Paket;

@ApplicationScoped
public class PaketRepository implements PanacheRepository<Paket> {
}
