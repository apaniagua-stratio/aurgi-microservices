package com.stratio.microservice.motortownimport.service.model;

public class Productcsv {

    private final long id;
    private final String content;

    public Productcsv(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Productcsv(long id) {
        this.id=id;
        this.content = "81978;8019227212723;TPIR2455018100W0R;245/50R18 100W EF.C FR.B R.71 2127200 PIRELLI;2127200;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;PIRELLI;CINTURATO P7;0;18;245;50;W;100;C;B;71;2;R;;208,5;http://webapi.aurgi.com/Imagenes/imgL/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgM/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgS/8019227212723.jpg;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109PIRT18W";
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }


}
