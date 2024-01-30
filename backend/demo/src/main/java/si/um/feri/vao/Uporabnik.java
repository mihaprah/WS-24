package si.um.feri.vao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import si.um.feri.dto.UporabnikDto;

@Entity
@Data
@NoArgsConstructor
public class Uporabnik {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ime, priimek;

    @OneToOne()
    @JoinColumn(name = "paket_id")
    Paket paket = null;




    public Uporabnik(UporabnikDto dto) {
        setIme(dto.ime());
        setPriimek(dto.priimek());
    }

    public void updateFrom(UporabnikDto dto) {
        setIme(dto.ime());
        setPriimek(dto.priimek());
    }

    public UporabnikDto toDto() {
        return new UporabnikDto(id, ime, priimek);
    }

}