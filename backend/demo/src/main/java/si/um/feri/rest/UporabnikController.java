package si.um.feri.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.dao.PaketRepository;
import si.um.feri.dao.UporabnikRepository;
import si.um.feri.dto.UporabnikDto;
import si.um.feri.vao.Paket;
import si.um.feri.vao.Uporabnik;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UporabnikController {

    private static final Logger log = Logger.getLogger(UporabnikController.class.getName());

    @Inject
    UporabnikRepository uporabnikRepository;

    @Inject
    PaketRepository paketRepository;
    @GET
    public List<UporabnikDto> vsiUporabniki() {
        return uporabnikRepository.listAll()
                .stream()
                .map(Uporabnik::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response getUporabnikById(@PathParam("id") int id) {
        Uporabnik uporabnik = uporabnikRepository.findById((long) id);
        if (uporabnik == null) {
            log.info(() -> "/uporabniki/" + id + " ; Uporabnik ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Uporabnik ne obstaja!").build();
        }
        return Response.ok(uporabnik.toDto()).build();
    }

    @POST
    @Transactional
    public Response postUporabnik(UporabnikDto dto) {
        Uporabnik uporabnik = new Uporabnik(dto);
        uporabnikRepository.persist(uporabnik);
        return Response.ok(uporabnik.toDto()).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response putUporabnik(@PathParam("id") int id, UporabnikDto dto) {
        Uporabnik uporabnik = uporabnikRepository.findById((long) id);
        if (uporabnik == null) {
            log.info(() -> "/uporabniki/" + id + " ; Uporabnik ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Uporabnik ne obstaja!").build();
        }
        uporabnik.updateFrom(dto);
        uporabnikRepository.persist(uporabnik);
        return Response.ok(uporabnik.toDto()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUporabnik(@PathParam("id") int id) {
        Uporabnik uporabnik = uporabnikRepository.findById((long) id);
        if (uporabnik == null) {
            log.info(() -> "/uporabniki/" + id + " ; Uporabnik ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Uporabnik ne obstaja!").build();
        }
        uporabnikRepository.delete(uporabnik);
        return Response.ok("Uporabnik izbrisana!").build();
    }

    @PUT
    @Path("/{id}/paket/{paket_id}")
    @Transactional
    public Response putPaketToUporabnik(@PathParam("id") int id,@PathParam("paket_id") int paket_id)
    {
        Uporabnik uporabnik = uporabnikRepository.findById((long) id);
        Paket paket = paketRepository.findById((long) paket_id);
        if (uporabnik == null) {
            log.info(() -> "/uporabniki/" + id + " ; Uporabnik ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Uporabnik ne obstaja!").build();
        } else if (paket == null){
            log.info(() -> "/paketi/" + paket_id + " ; Paket ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Paket ne obstaja!").build();
        }
        uporabnik.setPaket(paket);
        uporabnikRepository.persist(uporabnik);
        return Response.ok(uporabnik.toDto()).build();


    }

}
