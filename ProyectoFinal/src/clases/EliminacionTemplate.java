/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/*
Realizado por: JORGE ENCALADA - DIEGO PANDO VARGAS - CHRISTIAN TORRES
*/
public abstract class EliminacionTemplate {
    
    public final String fileMETA = "filesBD\\META.bd";
    
    public final void operation(Object[] args){
        this.IdentificarParametros(args);
        this.RealizarOperacion();
        this.ActualizarMETA();
    }
    
    protected abstract void IdentificarParametros(Object[] args);
    protected abstract void RealizarOperacion();
    protected abstract void ActualizarMETA();
}
