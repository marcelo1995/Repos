/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Realizado por: JORGE ENCALADA - DIEGO PANDO VARGAS - CHRISTIAN TORRES
*/
public class EliminacionTabla extends EliminacionTemplate {

    String nombreTabla;

    @Override
    protected void IdentificarParametros(Object[] args) {
        this.nombreTabla = (String) args[0];
    }
    
    @Override
    protected void RealizarOperacion() {
        File file = new File(this.fileMETA);
        File eliminar = new File(file.getParent() + "\\" + this.nombreTabla + ".BD");
        String leer;
        String leer2=null;
        BufferedReader br=null;
        try {
            CsvReader a= new CsvReader("filesBD\\"+this.nombreTabla+".bd");
            
            br= new BufferedReader(new FileReader("filesBD\\"+this.nombreTabla+".bd"));
            leer= br.readLine();
            leer2=br.readLine();
            System.out.println(leer2);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EliminacionTabla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EliminacionTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(eliminar.exists()){
            if(eliminar.length()==0|| leer2==null){
                System.out.println("entro");
            eliminar.delete();
            
            
            }
            else{
                System.out.println("No se puede eliminar archivo");
                throw new SecurityException("No se puede eliminar el archivo");
                
            }
        }
    }

    @Override
    protected void ActualizarMETA() {
            
        try{
            
            //Si el archivo META existe, leer y escribir su contenido
            if(new File(this.fileMETA).exists())
            {
                File file = new File(this.fileMETA);
                
                //Renombrar el archivo
                CsvReader FX = new CsvReader(file.getParent() + "\\" + "META.BD");
                
                file.setWritable(true);
                file.renameTo(new File(file.getParent() + "\\" + "fileAuxMeta77.BD"));
                CsvReader flujoEntrada = new CsvReader(file.getParent() + "\\" + "fileAuxMeta77.BD");
                
                //Lectura y escritura
                PrintWriter flujoSalida = new PrintWriter(this.fileMETA);//Crear el archivo luego de superar la condicion
                while(flujoEntrada.readRecord()){
                    if(!this.nombreTabla.equals(flujoEntrada.get(0)))
                        flujoSalida.println(flujoEntrada.getRawRecord());
                }
                    
                flujoEntrada.close();
                flujoSalida.close();
                
                //Borrar el archivo auxiliar
                new File(file.getParent() + "\\" + "fileAuxMeta77.BD").delete();
            }
        } catch (FileNotFoundException ex) {
            throw new Error("Lo sentimos, algo salio mal con los archivos internos");
        } catch (IOException ex) {
            throw new Error("Lo sentimos, algo salio mal con los archivos internos");
        }
        
        File file = new File(this.fileMETA);
        File eliminar = new File(file.getParent() + "\\" + this.nombreTabla + ".BD");
        
        String leer;
        String leer2=null;
        BufferedReader br=null;
        try {
            CsvReader a= new CsvReader("filesBD\\"+this.nombreTabla+".bd");
            
            br= new BufferedReader(new FileReader("filesBD\\"+this.nombreTabla+".bd"));
            leer= br.readLine();
            leer2=br.readLine();
            System.out.println(leer2);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EliminacionTabla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EliminacionTabla.class.getName()).log(Level.SEVERE, null, ex);
        
    }
        if(eliminar.exists()){
            if(eliminar.length()==0|| leer2==null){
            eliminar.delete();
                System.out.println("entro");
            
            
            }
            else{
                System.out.println("No se puede eliminar archivo");
                throw new SecurityException("No se puede eliminar el archivo");
                
            }
        }
        
    
}
}