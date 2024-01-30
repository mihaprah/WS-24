package si.um.feri.vao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.feri.dto.PaketDto;

@Entity
@Data
@NoArgsConstructor
public class Paket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String naziv, tip, opis;
    private int kolicina_prenosa;
    private Double cena;

    public Paket(PaketDto dto) {
        setNaziv(dto.naziv());
        setTip(dto.tip());
        setOpis(dto.opis());
        setKolicina_prenosa(dto.kolicina_prenosa());
        setCena(dto.cena());
    }

    public void updateFrom(PaketDto dto) {
        setNaziv(dto.naziv());
        setTip(dto.tip());
        setOpis(dto.opis());
        setKolicina_prenosa(dto.kolicina_prenosa());
        setCena(dto.cena());
    }

    public PaketDto toDto() {
        return new PaketDto(id, naziv, tip, opis, kolicina_prenosa, cena);
    }
}