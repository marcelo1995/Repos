/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/*
Realizado por: JORGE ENCALADA - DIEGO PANDO VARGAS - CHRISTIAN TORRES
*/

public interface ProcesosFactory {
    public CreacionTemplate creacionProceso();
    public ModificacionTemplate modificacionProceso();
    public EliminacionTemplate eliminacionProceso();
}
