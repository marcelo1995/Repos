/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
Realizado por: JORGE ENCALADA - DIEGO PANDO VARGAS - CHRISTIAN TORRES
*/

package clases;

import GUI.Principal;
import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//los import para la encriptacion
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//termina los import para la encriptacion 


public class Gestion {

    static int caso = 0;
    static final int CREARTABLA = 101;
    static final int MODIFICARTABLA = 102;
    static final int ELIMINARTABLA = 103;

    static final int INSERTAREN = 201;
    static final int ACTUALIZARREGISTRO = 202;
    static final int BORRARREGISTRO = 203;

    static final int SELECCIONARTABLAS = 301;
    static final int JOIN = 302;

    public void solicitudAceptada(Object[] args) throws Exception {

        //LLAMAR AL ABSTRACT FACTORY
        // TODO add your handling code here:
        if (args.length <= 0) {
            throw new Error("Lo sentimos, algo ha salido mal");
        }

        String caso = (String) args[0];
        System.out.println("caso valor:" + caso);
        System.out.println("------------------------------------------");
        switch (caso) {
            case "CREARTABLA": {
                String nombreTabla = (String) args[1];

                ArrayList<String> campos = (ArrayList<String>) (List) args[2];
                String campoClave = (String) args[3]; //la lista para guardar la longitud de los campo
                ArrayList<Integer> longitudes = (ArrayList<Integer>) (List) args[4];
                System.out.println("Nombre tabla: " + nombreTabla);
                System.out.println("Campos: " + campos);
                System.out.println("Campo clave: " + campoClave);
                System.out.println("Longitudes: " + longitudes);

                //Instanciación a través del Abstract Factory
                ProcesosFactory factory = new TablaFactory();
                CreacionTemplate crear = factory.creacionProceso();
                System.out.println("LLego hasta aqui abstract");
                Object[] arg = {args[1], args[2], args[3], args[4]};
                crear.operation(arg);
                Principal.estado = "Se ha creado la tabla " + nombreTabla + "";
                break;
            }
            case "MODIFICARTABLA": {
                String nombreTabla = (String) args[1];
                String nombreCampo = (String) args[2];
                String nuevoCampo = (String) args[3];

                System.out.println("Tabla: " + nombreTabla);
                System.out.println("Nombre del campo a modificar: " + nombreCampo);
                System.out.println("Nuevo valor del campo: " + nuevoCampo);

                //Instanciación a través del Abstract Factory
                ProcesosFactory factory = new TablaFactory();
                ModificacionTemplate modificar = factory.modificacionProceso();
                Object[] arg = {args[1], args[2], args[3]};
                modificar.operation(arg);

                Principal.estado = "Se ha modificado la tabla " + nombreTabla + "";
                break;
            }
            case "ELIMINARTABLA": {
                String nombreTabla = (String) args[1];
                System.out.println("Tabla: " + nombreTabla);

                //Instanciación a través del Abstract Factory
                ProcesosFactory factory = new TablaFactory();
                EliminacionTemplate eliminar = factory.eliminacionProceso();
                Object[] arg = {args[1]};
                eliminar.operation(arg);

                Principal.estado = "Se ha eliminado la tabla " + nombreTabla + "";
                break;
            }
            case "INSERTAREN": {
                String nombreTabla = (String) args[1];
                ArrayList<String> valoresCampos = (ArrayList<String>) (List) args[2];

                System.out.println("Tabla: " + nombreTabla);
                System.out.println("Valores de los campos: " + valoresCampos);

                //Instanciación a través del Abstract Factory
                ProcesosFactory factory = new RegistroFactory();
                CreacionTemplate crear = factory.creacionProceso();
                Object[] arg = {args[1], args[2]};
                crear.operation(arg);

                Principal.estado = "Se agrego el registro a la tabla " + nombreTabla + "";
                break;
            }
            case "ACTUALIZARREGISTRO": {
                System.out.println("entro");
                String nombreTabla = (String) args[1];
                String campoClave = (String) args[2];
                String nombreCampo = (String) args[3];
                String valorCampo = (String) args[4];
                int s1 = (int) args[5];
                System.out.println("arg[5]" + s1);
                System.out.println("Tabla: " + nombreTabla);
                System.out.println("Campo clave: " + campoClave);
                System.out.println("Nombre del campo a modificar: " + nombreCampo);
                System.out.println("Nuevo valor del campo: " + valorCampo);

                //Instanciación a través del Abstract Factory
                ProcesosFactory factory = new RegistroFactory();
                ModificacionTemplate modificar = factory.modificacionProceso();
                Object[] arg = {args[1], args[2], args[3], args[4], args[5]};
                modificar.operation(arg);

                Principal.estado = "Se modifico un registro de la tabla " + nombreTabla + "";
                break;
            }
            case "BORRARREGISTRO": {
                String nombreTabla = (String) args[1];
                String campoClave = (String) args[2];

                System.out.println("Tabla: " + nombreTabla);
                System.out.println("Campo clave: " + campoClave);

                //Instanciación a través del Abstract Factory
                ProcesosFactory factory = new RegistroFactory();
                EliminacionTemplate eliminar = factory.eliminacionProceso();
                Object[] arg = {args[1], args[2], args[3]};
                eliminar.operation(arg);

                Principal.estado = "Se elimino un registro de la tabla " + nombreTabla + "";
                break;
            }
            case "SELECCIONAR": {
                String nombreTabla = (String) args[1];
                String nombreCampo = (String) args[2];
                String valorCampo = (String) args[3];

                System.out.println("Tabla: " + nombreTabla);
                System.out.println("Campo: " + nombreCampo);
                System.out.println("Valor: " + valorCampo);

                Object[] arg = {args[1], args[2], args[3], args[4]};
                try {
                    new SeleccionarTabla(arg).Mostrar();
                } catch (IOException ex) {
                    throw new Error(ex.getMessage());
                }

                Principal.estado = "Se seleccionaron registros de la tabla " + nombreTabla + "";
                break;
            }
            case "UNIR": {
                String nombreTabla1 = (String) args[1];
                String nombreTabla2 = (String) args[2];
                String nombreCampo = (String) args[3];

                System.out.println("Tabla 1: " + nombreTabla1);
                System.out.println("Tabla 2: " + nombreTabla2);
                System.out.println("Campo: " + nombreCampo);

                Object[] arg = {args[1], args[2], args[3]};
                new UnirTablas(arg).Visualizar();

                Principal.estado = "Se unieron las tablas " + nombreTabla1 + " y " + nombreTabla2;
                break;
            }
            default:
                throw new Error("Lo sentimos, algo ha salido mal");
        }
    }

    private boolean BuscarTabla(String nombreTabla) {
        if (!new File("filesBD\\META.bd").exists()) {
            return false;
        }
        String fileMETA = "filesBD\\META.bd";
        try {
            CsvReader ar = new CsvReader(fileMETA);
            while (ar.readRecord()) {
                if (ar.get(0).equals(nombreTabla)) {
                    ar.close();
                    return true;
                }
            }
            ar.close();
        } catch (FileNotFoundException ex) {
            throw new Error("Error, algo salio mal con los archivos internos");
        } catch (IOException ex) {
            throw new Error("Error, algo salio mal con los archivos internos");
        }
        return false;
    }

    public void Peticion(Object[] args) throws Exception {
        //El argumento en la posicion cero es el comando
        String comando = (String) args[0];
        System.out.println("valor de comando PETICION: " + comando);

//Asignacion tipo
        if (comando.replace(" ", "").contains("CREARTABLA")) {
            caso = CREARTABLA;
        } else if (comando.replace(" ", "").contains("MODIFICARTABLA")) {
            caso = MODIFICARTABLA;
        } else if (comando.replace(" ", "").contains("ELIMINARTABLA")) {
            caso = ELIMINARTABLA;
        } else if (comando.replace(" ", "").contains("INSERTAREN")) {
            caso = INSERTAREN;
        } else if (comando.replace(" ", "").contains("ACTUALIZARREGISTRO")) {
            caso = ACTUALIZARREGISTRO;
        } else if (comando.replace(" ", "").contains("BORRARREGISTRO")) {
            caso = BORRARREGISTRO;
        } else if (comando.replace(" ", "").contains("SELECCIONARDE")) {
            caso = SELECCIONARTABLAS;
        } else if (comando.replace(" ", "").contains("UNIR")) {
            caso = JOIN;
        } else {
            throw new SecurityException("Lo sentimos, no se ha entendido esa orden.");
        }

        //SENTENCIAS DEL LENGUAJE: 
        switch (caso) {
            case CREARTABLA: {
                int i = "CREAR TABLA ".length();
                String nombreTabla = null;
                String campoClave = null;
                //int longitudCampos = 0;

                List<String> campos = new ArrayList<>();
                List<Integer> longitudes = new ArrayList<>();

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                StringBuffer atributo = new StringBuffer();
                String longiClave = comando;

                //Nombre de la tabla
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }
                //Palabra campo
                nombreTabla = atributo.toString();
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                //Palabra reservada CAMPOS
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                if (!atributo.toString().equals("CAMPOS")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada CAMPOS");
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                ///aqui le meti mano al codigo
                //longiClave
                //falta la exepcion por si no se encuantra la palabra reservada
                List<String> camposEncriptar = new ArrayList<>();
                int inicio = longiClave.indexOf("ENCRIPTADO");
                longiClave = longiClave + ".";
                int fin = longiClave.indexOf(".");
                String stringCamposEncriptar = longiClave.substring(inicio, fin).replace("ENCRIPTADO", " ").replace(",", " ");
                System.out.println("campos encriptar . " + stringCamposEncriptar);
                String nombreCampo = "";
                for (int k = 2; k < stringCamposEncriptar.length(); k++) {
                    if (stringCamposEncriptar.charAt(k) != ' ') {
                        nombreCampo = nombreCampo + stringCamposEncriptar.charAt(k);
                    } else {
                        camposEncriptar.add(nombreCampo);
                        nombreCampo = "";
                    }
                }

                ///aqui termino de meterle mano al codigo
                //Listado de Campos
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    atributo.append(comando.charAt(i));
                }

                if (!atributo.toString().contains("CLAVE")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada CLAVE");
                }
                //Listado de campos
                String coma2 = longiClave.substring(longiClave.indexOf("CAMPOS"), longiClave.indexOf("CLAVE"));
                String comandos2 = coma2.replace("CAMPOS", "").replaceAll(" ", "");
                String comando3 = comandos2 + ",";
                String comando2 = atributo.append(",").toString().replace(" ", "");

                int inicioClave = longiClave.indexOf("CLAVE");
                int finClave = longiClave.indexOf("LONGITUD");
                String valorClave = longiClave.substring(inicioClave, finClave).replaceAll(" ", "").replace("CLAVE", "");

                System.out.println("Valor campo clave :" + valorClave);
                atributo = new StringBuffer();
                for (i = 0; i < comando3.length(); i++) {
                    if (comando3.charAt(i) != ',') {
                        atributo.append(comando3.charAt(i));
                    } else {
                        if (stringCamposEncriptar.contains(atributo)) {
                            if (atributo.toString().equals(valorClave)) {
                                valorClave = valorClave + "T";
                            } else {
                                valorClave = valorClave + "F";
                            }
                            atributo.append("T");
                        } else {
                            atributo.append("F");
                        }
                        campos.add(atributo.toString());
                        atributo = new StringBuffer();
                    }
                }
                System.out.println("valorClaveFinal:" + valorClave);
                if (campos.contains(valorClave) == false) {
                    throw new SecurityException("El CAMPO CLAVE no se fue encontrado");
                }
                if (valorClave.charAt(valorClave.length() - 1) == 'T') {
                    throw new SecurityException("El CAMPO CLAVE no puede ser encriptado");

                }
                int inicioLongitud = longiClave.indexOf("LONGITUD");
                int finLongitud = longiClave.indexOf("ENCRIPTADO");
//la lista de las longitudes
                //en longiClave se guarda todo el string de la entrada
                //LONGITUD DE LOS CAMPOS

                String valoresLongitudesEntrada1 = longiClave.substring(longiClave.indexOf("LONGITUD"), longiClave.indexOf("ENCRIPTADO"));
                String valoresLongitudesCadena = valoresLongitudesEntrada1.replace("LONGITUD", "").replaceAll(" ", "");
                String Vlongitudes = valoresLongitudesCadena + ",";
                atributo = new StringBuffer();
                System.out.println("xxxx :\n" + Vlongitudes);

                for (int k = 0; k < Vlongitudes.length(); k++) {
                    System.out.println("entra al for valor:" + k);

                    if (Vlongitudes.charAt(k) != ',') {
                        System.out.println("entro al if");

                        atributo.append(Vlongitudes.charAt(k));

                    } else {
                        try {
                            int longitudCadaUno = Integer.parseInt(atributo.toString());
                        }//si en ves de numero se ingreso un entero
                        catch (Exception e) {
                            throw new SecurityException("Error, la longitud ingresada es incorrecta ");
                        }

                        String v = atributo.toString();
                        int validar = Integer.parseInt(v);
                        System.out.println("validar:" + validar);

                        if (validar <= 0) {
                            throw new SecurityException("Error, la longitud ingresada no puede ser negativa o cero");
                        }
                        longitudes.add(validar);

                        atributo = new StringBuffer();
                    }
                }
                if (longitudes.size() != campos.size()) {
                    throw new SecurityException("Error, El numero de longitudes es diferente al numero de campos");

                }

                System.out.println("valores de  longitudes:  " + longitudes);
//String valor=(longiClave.substring(longiClave.indexOf("LONGITUD"),longiClave.indexOf("ENCRIPTAR"))).replace(" ", "");
                //palabra reservada ENCRIPTADO
                //aqui lo de campo clave

                //Validacion de tabla existente
                if (this.BuscarTabla(nombreTabla)) {
                    throw new SecurityException("Error, el nombre de la tabla que se desea crear ya existe");
                }
                /**
                 * Solicitud aceptada correctamente
                 *
                 */
                Object[] arg = {"CREARTABLA", nombreTabla, campos, valorClave, longitudes, camposEncriptar};
                solicitudAceptada(arg);

                break;
            }
            case MODIFICARTABLA://----------------------------------------------
            {
                int i = "MODIFICAR TABLA ".length();

                String nombreTabla = null;
                String nombreCampo = null;
                String nuevoCampo = null;

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                StringBuffer atributo = new StringBuffer();

                //Nombre de la tabla
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreTabla = atributo.toString();

                if (nombreTabla.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre de la tabla");
                }

                //Palabra reservada Campo
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                if (!atributo.toString().equals("CAMPO")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada CAMPO");
                }

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                //Campo
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreCampo = atributo.toString();
                if (nombreCampo.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre del campo a modificar");
                }

                //Palabra reservada POR
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                if (!atributo.toString().equals("POR")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada POR");
                }

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                //Nuevo valor del campo
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nuevoCampo = atributo.toString();
                if (nuevoCampo.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nuevo valor del campo");
                }

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        throw new SecurityException("Error, existen caracteres de mas en la sentencia");
                    }
                }

                //Validacion de tabla existente
                if (!this.BuscarTabla(nombreTabla)) {
                    throw new SecurityException("Error, el nombre de la tabla especificada no existe");
                }

                //Validacion campo existente
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //Validacion tabla con registros
                String validarUbicacionCampo = null;
                BufferedReader br1 = null;
                try {
                    br1 = new BufferedReader(new FileReader("filesBD\\" + nombreTabla + ".bd"));
                    validarUbicacionCampo = br1.readLine();
                    br1.close();
                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error inesperado, algo salio mal");
                }
                System.out.println("validarUbicaion:" + validarUbicacionCampo);

                List<String> CamposPosicionesArchivo = new ArrayList<>();
                validarUbicacionCampo = validarUbicacionCampo + ",";
                atributo = new StringBuffer();
                for (int l = 0; l < validarUbicacionCampo.length(); l++) {
                    if (validarUbicacionCampo.charAt(l) != ',') {
                        atributo.append(validarUbicacionCampo.charAt(l));
                    } else {

                        CamposPosicionesArchivo.add(atributo.toString().replaceAll(" ", ""));
                        atributo = new StringBuffer();

                    }
                }
                System.out.println("campos lista:" + CamposPosicionesArchivo);
//desde aqui la validacion 
                String validar = CamposPosicionesArchivo.get(CamposPosicionesArchivo.indexOf(nombreCampo));
                if ("T".equals(validar.substring(validar.length() - 1))) {
                    throw new SecurityException("No se puede modificar un campo encriptado");
                }
                System.out.println("----------------------------");
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                String fileMETA = "filesBD\\META.bd";
                //validacion del campo clave
                boolean boolClave = false;
                try {
                    CsvReader ar = new CsvReader(fileMETA);
                    while (ar.readRecord()) {
                        if (ar.get(0).equals(nombreTabla)) {
                            if (ar.get(2).equals(nombreCampo)) {
                                boolClave = true;
                            }
                        }

                    }
                    ar.close();
                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                }
                if (boolClave == true) {
                    throw new SecurityException("No se puede modificar el CAMPO CLAVE");
                }
                if (nombreCampo.equals(nuevoCampo)) {
                    throw new SecurityException("El campo especificado ya existe");

                }
                /**
                 * Solicitud aceptada correctamente
                 *
                 */
                Object[] arg = {"MODIFICARTABLA", nombreTabla, nombreCampo, nuevoCampo};
                solicitudAceptada(arg);

                break;
            }
            case ELIMINARTABLA: //----------------------------------------------
            {
                String nombreTabla = null;

                int i = "ELIMINAR TABLA ".length();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                StringBuffer atributo = new StringBuffer();

                //Nombre de la tabla
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreTabla = atributo.toString();
                if (nombreTabla.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre de la tabla");
                }

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        throw new SecurityException("Error, existen caracteres de mas en la sentencia");
                    }
                }

                //Validacion de tabla existente
                if (!this.BuscarTabla(nombreTabla)) {
                    throw new SecurityException("Error, el nombre de la tabla especificada no existe");
                }

                /**
                 * Solicitud aceptada correctamente
                 *
                 */
                Object[] arg = {"ELIMINARTABLA", nombreTabla};
                solicitudAceptada(arg);

                break;
            }
            case INSERTAREN: //----------------------------------------------
            {
                int i = "INSERTAR EN ".length();

                String nombreTabla = null;
                List<String> valoresCampos = new ArrayList<>();//lista donde se guardaran los campos
                List<Integer> longitudesArchivo = new ArrayList<>();

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                System.out.println("#############");
                System.out.println("comando CrearRegistro :" + comando);

                System.out.println("#############");
                StringBuffer atributo = new StringBuffer();

                //Nombre de la tabla
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreTabla = atributo.toString();

                if (nombreTabla.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre de la tabla");
                }

                atributo = new StringBuffer();

                //Palabra reservada CLAVE
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                if (!atributo.toString().equals("VALORES")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada VALORES");
                }

                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    }
                }

                String valClave = atributo.toString();
                System.out.println("******************VALCLAVE****"+valClave);
                //comando=comando+",";

                //Validaciones longitud y numero de campos
                //int nroCampos = - 1;
                String campoClave = null;
                String fileMETA = "filesBD\\META.bd";
                BufferedReader br = null;
                try {
                    CsvReader ar = new CsvReader(fileMETA);
                    //br = new BufferedReader(new FileReader(fileMETA));
                    String line = "";

                    while (ar.readRecord()) {
                        if (ar.get(0).equals(nombreTabla)) {
                            // nroCampos = ar.getColumnCount() - 4;
                            campoClave = ar.get(2);
                            line = ar.getRawRecord();//obtiene la linea de registro
                        }
                    }
                    //System.out.println("linea:" + line);
                    //para la obtencion de los 
                    ar.close();
                    System.out.println("linea:" + line);
                    int inicio = 0, fin = 0;
                    for (int t = 0; t < line.length(); t++) {
                        if (line.charAt(t) == '*') {
                            inicio = t;
                        }
                        if (line.charAt(t) == '/') {
                            fin = t;
                        }
                    }
                    System.out.println("inicio:" + inicio);

                    System.out.println("fin:" + fin);

                    String longi = line.substring(inicio, fin);
                    System.out.println("longi" + longi);
                    String longiCadena = longi.replace("*", "") + ",";

                    System.out.println("longitudes:" + longiCadena);

                    atributo = new StringBuffer();
                    for (int l = 0; l < longiCadena.length(); l++) {
                        if (longiCadena.charAt(l) != ',') {
                            atributo.append(longiCadena.charAt(l));
                        } else {
                            String d = atributo.toString();
                            longitudesArchivo.add(Integer.parseInt(d));
                            atributo = new StringBuffer();
                        }
                    }
                    System.out.println("lista de longitudes:" + longitudesArchivo);
                    //------------------------------------------

                    //Valores de campos
//aqui se ingresan los valores de cada campo le meti mano al codigo
                    String valores = valClave + ",";
                    System.out.println("Llego :" + valores);
                    atributo = new StringBuffer();
                    int contador = 0;
                    for (int j = 0; j < valores.length(); j++) {
                        if (valores.charAt(j) != ',') {
                            atributo.append(valores.charAt(j));
                        } else {
                            //int va=Integer.parseInt(atributo.toString());
                            System.out.println("longitud del campo en CREAR REGISTRO:" + atributo.toString().length());
                            System.out.println("Longitud del campo CREAR TABLA:" + longitudesArchivo.get(contador));
                            //Validacion valores null

                            if (atributo.toString().length() > longitudesArchivo.get(contador)) {
                                throw new SecurityException("Error, uno de los valores de los campos exede su longitud");
                            } else {
                                contador++;
                                valoresCampos.add(atributo.toString());
                                if (valoresCampos.contains("")) {
                                    throw new SecurityException("Error, uno de los valores de campos es nulo");
                                }

                                atributo = new StringBuffer();
                            }

                        }
                    }

                    System.out.println("contador:" + contador);

                    System.out.println("SI valido correctamente");

                    System.out.println("valoresCampos :" + valoresCampos);

                    //Validacion de tabla existente
                    if (!this.BuscarTabla(nombreTabla)) {
                        throw new SecurityException("Error, el nombre de la tabla especificada no existe");
                    }
                } catch (IOException | NumberFormatException e) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                }

                if (/*nroCampos == -1 ||*/campoClave == null) {
                    throw new SecurityException("Error interno, no se han encontrado los datos de la tabla especificada");
                }
                //Validacion de que el campo clave no se repita
                try {
//codigo de la encriptacion llamadas a la clase principal
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(128);
                    SecretKey key = keyGenerator.generateKey();
                    String clave = "Programacion3Grupo2";//clave >= 16 caracteres para la encriptcion
                    key = new SecretKeySpec(clave.getBytes(), 0, 16, "AES");
                    Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    aes.init(Cipher.ENCRYPT_MODE, (java.security.Key) key);
//fin del codigo de la encriptación
                    //String clave = "xxxxxxxxxxxxxxxx";//clave >= 16 caracteres
                    //longitudesArchivo la lista de las longitudes para cada campo
                    CsvReader ar = new CsvReader("filesBD\\" + nombreTabla + ".BD");
                    int posicion = -1;
                    ar.readRecord();
                    for (int k = 0; k < ar.getColumnCount(); k++) {
                        //val = va a tener el valor del campo
                        System.out.println("val: " + ar.get(k));

//ar.get obtiene el nombre del campo                        
//String camp=ar.get(k);
                        //if(camp.charAt(camp.length()-1)=='F'){
                        //}
                        if ((ar.get(k).charAt((ar.get(k)).length() - 1)) == 'T') {
                            String valorEncriptar = valoresCampos.get(k);
                            //aqui debe ir la encriptar la palabra valor encriptar
                            byte[] ValorEncriptado = aes.doFinal(valorEncriptar.getBytes());
                            String encri = "";
                            for (byte b : ValorEncriptado) {
                                encri = encri + Integer.toHexString(0xFF & b);
                                //    System.out.print(Integer.toHexString(0xFF & b));
                            }
                            valoresCampos.set(k, encri);
                            //valoresCampos.remove(k)//borro el elemento
                            System.out.println("valores a encriptar. " + valorEncriptar);
                        }
                        if (ar.get(k).equals(campoClave)) {
                            posicion = k;
                        }
                    }
                    while (ar.readRecord()) {
                        if (ar.get(posicion).equals(valoresCampos.get(posicion))) {
                            throw new SecurityException("Error, el valor correspondiente al campo clave ya existe");
                        }
                    }
                    //ojo para la validacion
                    ar.close();

                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error inesperado, algo salio mal");
                }

                /**
                 * Solicitud aceptada correctamente
                 *
                 */
                Object[] arg = {"INSERTAREN", nombreTabla, valoresCampos};
                System.out.println("se registro");
                solicitudAceptada(arg);
                System.out.println("Ingreso a peticion 1");

                break;
            }
            case ACTUALIZARREGISTRO: //------------------------------------------
            {
                int i = "ACTUALIZAR REGISTRO ".length();

                String nombreTabla = null;
                String valorCampoClaveFinal = "";
                String nombreCampo = null;
                String valorCampo = null;
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                //en comando ya tengo todas la linea de ingreso
                //puedo cortar con substring e index of para obteber los datos nesesarios y las validaciones
                System.out.println("Comando de modificar registro:" + comando);
                
                StringBuffer atributo = new StringBuffer();

                //Nombre de la tabla
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreTabla = atributo.toString();
                System.out.println("Nombre de la tabla:" + nombreTabla);

//_______________________________________________________________________________
//obtiene el valor de la clave de la tabla de registro
                String line = "";

                try {

                    String fileMETA = "filesBD\\META.bd";
                    CsvReader ar = new CsvReader(fileMETA);
                    while (ar.readRecord()) {
                        if (ar.get(0).equals(nombreTabla)) {
                            valorCampoClaveFinal = ar.get(2);
                            line = ar.getRawRecord();
                        }
                    }

                    ar.close();

                } catch (IOException | NumberFormatException e) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                }
//______________________________________________________________________________

//desde aqui comeinza la validacioon de sintaxis solo con las recortes de las cadenas y declarando las variables
// la variable comando tiene toda la cadena ingresada
                try {
                    String nombreCampo1 = comando.substring(comando.indexOf("CAMPO"), comando.indexOf("POR"));
                    nombreCampo = nombreCampo1.replace("CAMPO", "").replaceAll(" ", "");
                    //valor nuevo del campo
                    comando = comando + "@";
                    String valorNuevo = comando.substring(comando.indexOf("POR"), comando.indexOf("@"));
                    valorCampo = valorNuevo.replace("POR", "").replaceAll(" ", "");

                } catch (Exception e) {
                    throw new SecurityException("Error de sintaxis");
                }
                System.out.println("lineaMetadato:" + line);
                System.out.println("nombre del campo:" + nombreCampo);
                System.out.println("valor de clave campo:" + valorCampoClaveFinal);
                System.out.println("valor del campo Nuevo:" + valorCampo);
                if (valorCampoClaveFinal.equals(nombreCampo)) {
                    throw new SecurityException("No se puede modificar el CAMPO CLAVE");
                }

                //aqui deberia ser algo igual a obtener las longitudes
                //Validacion tabla con registros
                String validarUbicacionCampo = null;
                BufferedReader br1 = null;
                try {

                    CsvReader read = new CsvReader("filesBD\\" + nombreTabla + ".bd");
                    br1 = new BufferedReader(new FileReader("filesBD\\" + nombreTabla + ".bd"));
                    validarUbicacionCampo = br1.readLine();
                    int cont = 0;
                    while (read.readRecord()) {
                        cont++;

                    }
                    if (cont <= 1) {
                        throw new SecurityException("Error, la tabla especificada no cuenta con registros");
                    }
                    read.close();
                    br1.close();
                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error inesperado, algo salio mal");
                }
                System.out.println("validarUbicaion:" + validarUbicacionCampo);
                int posicionCampo;
                if (validarUbicacionCampo.contains(nombreCampo)) {
                    List<String> CamposPosicionesArchivo = new ArrayList<>();
                    validarUbicacionCampo = validarUbicacionCampo + ",";
                    atributo = new StringBuffer();
                    for (int l = 0; l < validarUbicacionCampo.length(); l++) {
                        if (validarUbicacionCampo.charAt(l) != ',') {
                            atributo.append(validarUbicacionCampo.charAt(l));
                        } else {

                            CamposPosicionesArchivo.add(atributo.toString());
                            atributo = new StringBuffer();

                        }
                    }
                    System.out.println("campos lista:" + CamposPosicionesArchivo);

                    posicionCampo = CamposPosicionesArchivo.indexOf(valorCampoClaveFinal);
                    String h = CamposPosicionesArchivo.get(CamposPosicionesArchivo.indexOf(nombreCampo));
                    if ("T".equals(h.substring(h.length() - 1))) {
                        throw new SecurityException("Error, no se puede modificar un campo encriptado");

                    }
                    System.out.println("posicion campo:" + posicionCampo);
                } else {
                    System.out.println("al fin");
                    throw new SecurityException("Error, no se encontró el campo");
                }

                System.out.println("campoCLAVE VALIDO llego:" + posicionCampo);
                System.out.println("----------------------------");
                System.out.println("nombreTabla:" + nombreCampo);
                System.out.println("valorCampoClave" + valorCampoClaveFinal);//mal
                System.out.println("nombreCampo" + nombreCampo);
                System.out.println("valorCampo" + valorCampo);
                System.out.println("posicionCampo" + posicionCampo);
                String g1 = comando.substring(comando.lastIndexOf("CLAVE"), comando.lastIndexOf("CAMPO"));
                String clave = g1.replace("CLAVE", "").replaceAll(" ", "");
                System.out.println("clave:" + clave);

                Object[] arg = {"ACTUALIZARREGISTRO", nombreTabla, clave, nombreCampo, valorCampo, posicionCampo};
                solicitudAceptada(arg);

            }

            case BORRARREGISTRO: //------------------------------------------
            {
                String nombreTabla = null;
                String campoClave = null;

                int i = "BORRARREGISTRO ".length();

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                StringBuffer atributo = new StringBuffer();

                //Nombre de la tabla
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreTabla = atributo.toString();
                System.out.println("nombreTabla:" + nombreTabla);

                if (nombreTabla.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre de la tabla");
                }

                atributo = new StringBuffer();

                //Palabra reservada CLAVE
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                if (!atributo.toString().equals("CLAVE")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada CLAVE");
                }

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                //Campo clave 
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                campoClave = atributo.toString();
                System.out.println("campo clave:" + campoClave);
                if (campoClave.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el campo clave");
                }

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        throw new SecurityException("Error, existen caracteres de mas en la sentencia");
                    }
                }

                //Validacion de tabla existente
                if (!this.BuscarTabla(nombreTabla)) {
                    throw new SecurityException("Error, el nombre de la tabla especificada no existe");
                }

                //Validacion tabla con registros
                try {

                    CsvReader read = new CsvReader("filesBD\\" + nombreTabla + ".bd");
                    int cont = 0;
                    while (read.readRecord()) {
                        cont++;
                    }
                    if (cont <= 1) {
                        throw new SecurityException("Error, la tabla especificada no cuenta con registros");
                    }
                    read.close();
                    read.close();
                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error inesperado, algo salio mal");
                }
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //Validacion tabla con registros

                String valorCampoClaveFinal = "";

                try {

                    String fileMETA = "filesBD\\META.bd";
                    CsvReader ar = new CsvReader(fileMETA);
                    while (ar.readRecord()) {
                        if (ar.get(0).equals(nombreTabla)) {
                            valorCampoClaveFinal = ar.get(2);
                        }
                    }

                    ar.close();

                } catch (IOException | NumberFormatException e) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                }
                String validarUbicacionCampo = null;
                BufferedReader br1 = null;
                try {

                    //    CsvReader read = new CsvReader("filesBD\\" + nombreTabla + ".bd");
                    br1 = new BufferedReader(new FileReader("filesBD\\" + nombreTabla + ".bd"));
                    validarUbicacionCampo = br1.readLine();

                    //  read.close();
                    br1.close();
                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error inesperado, algo salio mal");
                }
                System.out.println("validarUbicaion:" + validarUbicacionCampo);

                int posicionCampo;

                List<String> CamposPosicionesArchivo = new ArrayList<>();
                validarUbicacionCampo = validarUbicacionCampo + ",";
                atributo = new StringBuffer();
                for (int l = 0; l < validarUbicacionCampo.length(); l++) {
                    if (validarUbicacionCampo.charAt(l) != ',') {
                        atributo.append(validarUbicacionCampo.charAt(l));
                    } else {

                        CamposPosicionesArchivo.add(atributo.toString().replaceAll(" ", ""));
                        atributo = new StringBuffer();

                    }
                }
                System.out.println("campos lista:" + CamposPosicionesArchivo);

                posicionCampo = CamposPosicionesArchivo.indexOf(valorCampoClaveFinal);

                System.out.println("posicion campo:" + posicionCampo);

                System.out.println("campoCLAVE VALIDO llego:" + posicionCampo);
               /**
                 * Solicitud aceptada correctamente
                 *
                 */
                Object[] arg = {"BORRARREGISTRO", nombreTabla, campoClave, posicionCampo};
                solicitudAceptada(arg);

                break;
            }
            case SELECCIONARTABLAS://-------------------------------------------
            {
                int i = "SELECCIONAR DE ".length();

                String nombreTabla = null;
                String nombreCampo = null;
                String valorCampo = null;

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                StringBuffer atributo = new StringBuffer();

                //Nombre de la tabla
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreTabla = atributo.toString();

                if (nombreTabla.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre de la tabla");
                }

                atributo = new StringBuffer();

                //Palabra reservada DONDE
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                if (!atributo.toString().equals("DONDE")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada DONDE");
                }

                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    atributo.append(comando.charAt(i));
                }

                if (!atributo.toString().contains("=")) {
                    throw new SecurityException("Error en sintaxis, falta el operador =");
                }

                comando = atributo.toString().replace(" ", "");
                //Palabra del campo de atributo campo
                atributo = new StringBuffer();
                for (i = 0; i < comando.indexOf("="); i++) {
                    atributo.append(comando.charAt(i));
                }

                nombreCampo = atributo.toString();

                if (!comando.contains("\"")) {
                    throw new SecurityException("Error en sintaxis, faltan las comillas \" para especificar el valor de busqueda");
                }
                if (comando.charAt(i + 1) != '\"') {
                    throw new SecurityException("Error en sintaxis, faltan las comillas \" para especificar el valor de busqueda");
                }

                //Valor
                atributo = new StringBuffer();
                for (i = i + 2; i < comando.length(); i++) {
                    atributo.append(comando.charAt(i));
                }
                comando = atributo.toString();
                if (!comando.toString().contains("\"")) {
                    throw new SecurityException("Error en sintaxis, faltan las comillas \" para especificar el valor de busqueda");
                }

                atributo = new StringBuffer();
                for (i = 0; i < comando.indexOf("\""); i++) {
                    atributo.append(comando.charAt(i));
                }

                valorCampo = atributo.toString();
                if (nombreCampo.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre del campo");
                }

                if (valorCampo.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el valor del campo");
                }

                for (i = i + 1; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        throw new SecurityException("Error, existen caracteres de mas en la sentencia");
                    }
                }

                //Validacion de tabla existente
                if (!this.BuscarTabla(nombreTabla)) {
                    throw new SecurityException("Error, el nombre de la tabla especificada no existe");
                }

                //Validacion de que el campo especificado exista...
                //Validacion tabla con registros
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //Validacion tabla con registros
                String valorCampoClaveFinal = "";

                try {

                    String fileMETA = "filesBD\\META.bd";
                    CsvReader ar = new CsvReader(fileMETA);
                    while (ar.readRecord()) {
                        if (ar.get(0).equals(nombreTabla)) {
                            valorCampoClaveFinal = ar.get(2);
                        }
                    }

                    ar.close();

                } catch (IOException | NumberFormatException e) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                }
                String validarUbicacionCampo = null;
                BufferedReader br1 = null;
                try {

                    //    CsvReader read = new CsvReader("filesBD\\" + nombreTabla + ".bd");
                    br1 = new BufferedReader(new FileReader("filesBD\\" + nombreTabla + ".bd"));
                    validarUbicacionCampo = br1.readLine();

                    //  read.close();
                    br1.close();
                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error inesperado, algo salio mal");
                }
                System.out.println("validarUbicaion:" + validarUbicacionCampo);

                int posicionCampo;

                List<String> CamposPosicionesArchivo = new ArrayList<>();
                validarUbicacionCampo = validarUbicacionCampo + ",";
                atributo = new StringBuffer();
                for (int l = 0; l < validarUbicacionCampo.length(); l++) {
                    if (validarUbicacionCampo.charAt(l) != ',') {
                        atributo.append(validarUbicacionCampo.charAt(l));
                    } else {

                        CamposPosicionesArchivo.add(atributo.toString().replaceAll(" ", ""));
                        atributo = new StringBuffer();

                    }
                }
                System.out.println("campos lista:" + CamposPosicionesArchivo);
                System.out.println("Valor");
                posicionCampo = CamposPosicionesArchivo.indexOf(nombreCampo);

                System.out.println("posicion campo:" + posicionCampo);

                System.out.println("campoCLAVE VALIDO llego:" + posicionCampo);
                System.out.println("----------------------------");
                //bfjhbfjhsbfjhsgbfshgfhsjlsfu j ejfiusfj
                /**
                 * Solicitud aceptada correctamente
                 *
                 */
                Object[] arg = {"SELECCIONAR", nombreTabla, nombreCampo, valorCampo, posicionCampo};
                solicitudAceptada(arg);

                break;
            }
            case JOIN://--------------------------------------------------------
            {
                int i = "UNIR ".length();

                String nombreTabla1 = null;
                String nombreTabla2 = null;
                String nombreCampo = null;

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                //Listado de Campos
                StringBuffer atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    atributo.append(comando.charAt(i));
                }

                //Listado de campos
                String comando2 = atributo.toString().replace(" ", "");
                atributo = new StringBuffer();

                if (!comando.contains("POR")) {
                    throw new SecurityException("Error en sintaxis, falta la palabra reservada POR");
                }

                if (!comando.contains(",")) {
                    throw new SecurityException("Error en sintaxis, falta especificar el nombre de las tablas");
                }

                for (i = 0; i < comando2.indexOf("POR"); i++) {
                    if (comando2.charAt(i) != ',') {
                        atributo.append(comando2.charAt(i));
                    } else {
                        nombreTabla1 = atributo.toString();
                        atributo = new StringBuffer();
                        comando = comando.replace(nombreTabla1, "");
                    }
                }
                nombreTabla2 = atributo.toString();
                comando = comando.replace(nombreTabla2, "");
                if (nombreTabla1.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre de la primera tabla");
                }

                if (nombreTabla2.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el nombre de la segunda tabla");
                }

                //Palabra reservada POR
                atributo = new StringBuffer();
                i = comando.indexOf("POR") + 3;

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }

                //Nuevo valor del campo
                atributo = new StringBuffer();
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        break;
                    }
                }
                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        atributo.append(comando.charAt(i));
                    } else {
                        break;
                    }
                }

                nombreCampo = atributo.toString();
                if (nombreCampo.isEmpty()) {
                    throw new SecurityException("Error, no se ha especificado el factor de union");
                }

                for (i = i + 0; i < comando.length(); i++) {
                    if (comando.charAt(i) != ' ') {
                        throw new SecurityException("Error, existen caracteres de mas en la sentencia");
                    }
                }

                //Validacion de tabla1 existente
                if (!this.BuscarTabla(nombreTabla1)) {
                    throw new SecurityException("Error, la tabla " + nombreTabla1 + " no existe");
                }

                if (!this.BuscarTabla(nombreTabla2)) {
                    throw new SecurityException("Error, la tabla " + nombreTabla2 + " no existe");
                }

                //Validacion campo existentes en las tablas
                boolean estadoTabla1 = false;
                boolean estadoTabla2 = false;
                CsvReader read;
                CsvReader read2;
                try {

                    read = new CsvReader("filesBD\\" + nombreTabla1 + ".BD");
                    read.readRecord();
                    for (i = 0; i < read.getColumnCount(); i++) {
                        if (!read.getRawRecord().isEmpty()) {
                            if (read.get(i).equals(nombreCampo)) {
                                estadoTabla1 = true;
                                break;
                            }
                        }
                    }
                    read.close();
                    read2 = new CsvReader("filesBD\\" + nombreTabla2 + ".BD");
                    read2.readRecord();
                    for (i = 0; i < read2.getColumnCount(); i++) {
                        if (!read2.getRawRecord().isEmpty()) {
                            if (read2.get(i).equals(nombreCampo)) {
                                estadoTabla2 = true;
                                break;
                            }
                        }
                    }
                    read2.close();
                } catch (FileNotFoundException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                } catch (IOException ex) {
                    throw new Error("Error, algo salio mal con los archivos internos");
                }
                read2.close();
                read.close();

                if (estadoTabla1 == false) {
                    throw new SecurityException("Error, la tabla " + nombreTabla1 + " no posee el campo " + nombreCampo);
                }

                if (estadoTabla2 == false) {
                    throw new SecurityException("Error, la tabla " + nombreTabla2 + " no posee el campo " + nombreCampo);
                }

                /**
                 * Solicitud aceptada correctamente
                 *
                 */
                Object[] arg = {"UNIR", nombreTabla1, nombreTabla2, nombreCampo};
                solicitudAceptada(arg);

                break;
            }
            default:
                throw new SecurityException("Lo sentimos, no se ha entendido esa orden");
        }

        //Solicitud aprobada - Ubicar las invocaciones en cada uno de los casos
        //new GestionBDReal().Peticion(arg);
    }
}
