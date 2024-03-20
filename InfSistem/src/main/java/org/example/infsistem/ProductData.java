package org.example.infsistem;

public class ProductData {

    private Integer id;
    private Integer kolicina;
    private String status;
    private  String slika;
    private Double cena;
    private String tip;
    private String ime;



    public ProductData (Integer id, Integer kolicina, String status, String slika,Double cena,
                        String tip, String ime)
    {


        this.cena= cena;
        this.id = id;
        this.ime = ime;
        this.kolicina = kolicina;
        this.status = status;
        this.tip = tip;
        this.slika = slika;



    }


    public Integer getId() {
        return id;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public String getStatus() {
        return status;
    }

    public String getSlika() {
        return slika;
    }

    public Double getCena() {
        return cena;
    }

    public String getTip() {
        return tip;
    }

    public String getIme() {
        return ime;
    }
}
