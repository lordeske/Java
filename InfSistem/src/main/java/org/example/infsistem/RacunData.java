package org.example.infsistem;

public class RacunData {


    //Pravimo novi objekar Racun da bi mogli da proocitamo te podatke i onda ih smjestimo u ikonicu

    private Integer  idNarudzbe;

    private String imeKupca;

    private Double ukupnaCena;

    private String datum;
    private String hrana;


    public Integer getIdNarudzbe() {
        return idNarudzbe;
    }

    public String getImeKupca() {
        return imeKupca;
    }

    public Double getUkupnaCena() {
        return ukupnaCena;
    }

    public String getDatum() {
        return datum;
    }

    public RacunData(Integer idNarudzbe, String imeKupca, Double ukupnaCena, String datum, String hrana) {
        this.idNarudzbe = idNarudzbe;
        this.imeKupca = imeKupca;
        this.ukupnaCena = ukupnaCena;
        this.datum = datum;
        this.hrana = hrana;

    }

    public String getHrana() {
        return hrana;
    }
}
