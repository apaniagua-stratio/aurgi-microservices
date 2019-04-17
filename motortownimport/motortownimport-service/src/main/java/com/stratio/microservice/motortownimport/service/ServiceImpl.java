package com.stratio.microservice.motortownimport.service;

import com.stratio.microservice.motortownimport.service.model.Productcsv;
import com.stratio.microservice.motortownimport.service.model.ServiceInput;
import com.stratio.microservice.motortownimport.service.model.ServiceOutput;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ServiceImpl implements com.stratio.microservice.motortownimport.service.Service{

  @Value("${sftphost}")
  private String sftphost;

  @Value("${sftpuser}")
  private String sftpuser;

  @Value("${sftpkey}")
  private String sftpkey;


  @Override
  public ServiceOutput doSomething(ServiceInput input) {

    ServiceOutput serviceOutput = new ServiceOutput();

    return serviceOutput;

  }


  @Override
  public Productcsv[] getCsvExample() {

    //ServiceOutput serviceOutput = new ServiceOutput();
    //serviceOutput.exampleCsv="81978;8019227212723;TPIR2455018100W0R;245/50R18 100W EF.C FR.B R.71 2127200 PIRELLI;2127200;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;PIRELLI;CINTURATO P7;0;18;245;50;W;100;C;B;71;2;R;;208,5;http://webapi.aurgi.com/Imagenes/imgL/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgM/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgS/8019227212723.jpg;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109PIRT18W";
    //return serviceOutput;


    Productcsv[] prods = new Productcsv[5];
    prods[0]=new Productcsv(1,"81978;8019227212723;TPIR2455018100W0R;245/50R18 100W EF.C FR.B R.71 2127200 PIRELLI;2127200;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;PIRELLI;CINTURATO P7;0;18;245;50;W;100;C;B;71;2;R;;208,5;http://webapi.aurgi.com/Imagenes/imgL/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgM/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgS/8019227212723.jpg;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109PIRT18W");
    prods[1]=new Productcsv(1,"36661;4019238314212;TCON1856015084H00;185/60R15 EF.E FR. C R.70 84H 0350062  CONTI;350062;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;CONTINENTAL;PREM-CON 2;0;15;185;60;H;84;E;C;70;2;0;;71,9;http://webapi.aurgi.com/Imagenes/imgL/4019238314212.jpg;http://webapi.aurgi.com/Imagenes/imgM/4019238314212.jpg;http://webapi.aurgi.com/Imagenes/imgS/4019238314212.jpg;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109CONT15H");
    prods[2]=new Productcsv(1,"36693;3286347781414;TBRI2355518099V00;235/55 R18 99V EF.E FR.E R.71 77814 BRIDGESTONE;77814;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;BRIDGESTONE;ER-31;0;18;235;55;V;99;E;E;71;2;0;;147,9;;;;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109BRIT18V");
    prods[3]=new Productcsv(1,"36754;3286347581519;TBRI2953018098Y00;295/30 R18 98Y EF.F FR.B R.73 75815 BRIDGESTONE;75815;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;BRIDGESTONE;POTENZA S-02A;0;18;295;30;Y;98;F;B;73;2;0;;307,9;http://webapi.aurgi.com/Imagenes/imgL/3286347581519.jpg;http://webapi.aurgi.com/Imagenes/imgM/3286347581519.jpg;http://webapi.aurgi.com/Imagenes/imgS/3286347581519.jpg;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109BRIT18Y");
    prods[4]=new Productcsv(1,"38084;4019238314298;TCON2055516091H00;205/55R16 EF.E FR. B R.71 91H 0350087  CONTI;350087;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;CONTINENTAL;PREM-CON 2;0;16;205;55;H;91;E;B;71;2;0;;67,9;http://webapi.aurgi.com/Imagenes/imgL/4019238314298.jpg;http://webapi.aurgi.com/Imagenes/imgM/4019238314298.jpg;http://webapi.aurgi.com/Imagenes/imgS/4019238314298.jpg;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109CONT16H");

    return prods;

  }

  @Override
  public List<Productcsv> getMotortownCsv(String filepath) {

    //ServiceOutput serviceOutput = new ServiceOutput();
    //serviceOutput.exampleCsv="81978;8019227212723;TPIR2455018100W0R;245/50R18 100W EF.C FR.B R.71 2127200 PIRELLI;2127200;;Activo;NEUMATICOS;TURISMO;False;RUEDAS;RUEDAS;PIRELLI;CINTURATO P7;0;18;245;50;W;100;C;B;71;2;R;;208,5;http://webapi.aurgi.com/Imagenes/imgL/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgM/8019227212723.jpg;http://webapi.aurgi.com/Imagenes/imgS/8019227212723.jpg;True;False;False;False;False;42398,9101013,9101005,18434;9101021,9101043,69813,9101009;False;False;False;109PIRT18W";
    //return serviceOutput;

    List<Productcsv> productos= new ArrayList<Productcsv>();
    List<String> csvList = new ArrayList<String>();


    SftpReader reader=new SftpReader();

    csvList = reader.readCsvFileFromSftp(sftpuser,sftphost,sftpkey,filepath);

    Iterator<String> csvIterator = csvList.iterator();
    while (csvIterator.hasNext()) {

      Productcsv producto = new Productcsv(1,csvIterator.next());
      productos.add(producto);
      //System.out.println(csvIterator.next());
    }

    return productos;

  }



}
