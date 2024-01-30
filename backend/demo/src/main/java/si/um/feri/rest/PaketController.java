package si.um.feri.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.dao.PaketRepository;
import si.um.feri.dto.PaketDto;
import si.um.feri.vao.Paket;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/paketi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaketController {

    private static final Logger log = Logger.getLogger(PaketController.class.getName());

    @Inject
    PaketRepository paketRepository;

    @GET
    public List<PaketDto> vsiPaketi() {
        return paketRepository.listAll()
                .stream()
                .map(Paket::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response getPaketbyId(@PathParam("id") int id){
        Paket paket = paketRepository.findById((long) id);
        if(paket == null){
            log.info(() -> "/paketi/" + id + " ; Paket ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Paket ne obstaja!").build();
        }

        return Response.ok(paket.toDto()).build();

    }

    @POST
    @Transactional
    public Response postPaket(PaketDto dto){
        Paket paket = new Paket(dto);
        paketRepository.persist(paket);
        return Response.ok(paket.toDto()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response putPaket(@PathParam("id") int id, PaketDto dto){
        Paket paket = paketRepository.findById((long) id);
        if(paket == null){
            log.info(() -> "/paketi/" + id + " ; Paket ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Paket ne obstaja!").build();
        }

        paket.updateFrom(dto);
        paketRepository.persist(paket);
        return  Response.ok(paket.toDto()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePaket(@PathParam("id") int id){
        Paket paket = paketRepository.findById((long) id);
        if(paket == null){
            log.info(() -> "/paketi/" + id + " ; Paket ne obstaja!");
            return Response.status(Response.Status.NOT_FOUND).entity("Paket ne obstaja!").build();
        }
        paketRepository.delete(paket);
        return Response.ok("Paket je izbrisan!").build();
    }




}
