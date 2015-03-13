/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterCalc;



import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

/**
 * Class that returns the values of y' based on the equation. The basic task of this class is to store the differential equation. 
 * Special care should be taken while making changes to this class, as performance of the app directly depends on this.
 * @author VAIO-1
 */
public class DE implements FirstOrderDifferentialEquations {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.08E8A872-F779-6C68-9E92-95BCCE2A5FA5]
    // </editor-fold> 
    private static String gFunction;
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.965BCB70-FBDC-7DD7-A03E-3B5EF6015188]
    // </editor-fold> 
    private static String hFunction;
    
    public static Evaluator ev;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.148B6411-A92D-6849-01E0-0778F8F01321]
    // </editor-fold> 
    public DE() {
         ev = new Evaluator();


    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.784D12D5-3C51-16DF-45CD-7E1595D35371]
    // </editor-fold> 
    public static void setGFunction(String val) {
        gFunction = val;
    }

    public static String getGFunction() {
        return gFunction;
    }

    public static void setHFunction(String hFunc) {
        hFunction = hFunc;
    }

    public static String getHFunction() {
        return hFunction;
    }


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C83DBAB9-5C37-61B4-9E67-FADFFADE06E5]
    // </editor-fold> 
    

    

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.ED7709FC-08F3-25FB-6DF7-3872DF66B7B7]
    // </editor-fold> 
    public double computeGx(double value) {
        return 0.0;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CEE91D81-9E81-4FDC-EA7A-38AF9D29736F]
    // </editor-fold> 
    public double computeHx(double value) {
        return 0.0;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    
    /**
     * Based on the DE, returns the value of the derivative of the dependent variable given the value of independent variable.
     * @param t independent variable
     * @param y dependent variable
     * @param yDot the computed derivate of y
     * @throws MaxCountExceededException
     * @throws DimensionMismatchException 
     */
    @Override
    public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
        // return the computed derivative
        // compute the g expression of t
       ev.putVariable("x", Double.toString(t));
       try {
            // no need of Initial values at this place.
            yDot[0] = (-1*Double.parseDouble(ev.evaluate(gFunction)))*y[0] + Double.parseDouble(ev.evaluate(hFunction));
            
            // compute the value of yDot
            // compute the value of yDot
            
        } catch (EvaluationException ex) {
            Logger.getLogger(DE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
